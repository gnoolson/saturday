package gnoolson.saturday.script.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ScriptUpdatedEvent;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.ScriptName;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.script.model.entity.Script;
import gnoolson.saturday.script.model.exception.ScriptNotFoundException;
import gnoolson.saturday.script.port.inbound.UpdateScriptUseCase;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;


@Log4j2
@RequiredArgsConstructor
public class UpdateScriptUseCaseImpl implements UpdateScriptUseCase {

    private final ScriptRepositoryGateway scriptRepositoryGateway;
    private final TransactionStarter transactionStarter;
    private final Locker locker;
    private final EventBus eventBus;

    /*
     *
     *
     * */
    @Override
    public void execute(ScriptDto scriptDto) {
        Script script = scriptRepositoryGateway.find(scriptDto.getId()).orElseThrow(() -> new ScriptNotFoundException(scriptDto.getId()));
        String[] keys = generateKeys(scriptDto, script);

        try (Locker.LockHandle ignore = locker.lockIds(keys)) {
            checkName(scriptDto.getProjectId(), script.getName(), scriptDto.getName());
            checkErrorHandler(script, scriptDto.getErrorEventHandler());

            script.update(scriptDto.getName(),
                    scriptDto.getDescription(),
                    scriptDto.getCachingTime(),
                    scriptDto.isAutostart(),
                    scriptDto.getErrorEventHandler());

            transactionStarter.doIt(() -> {
                transactionStarter.doAfterTransactionCommit(() -> {
                    eventBus.emit(new ScriptUpdatedEvent(script.getId()));
                });
                scriptRepositoryGateway.save(script);
            });
        }
    }

    /*
     *
     *
     * */
    private String[] generateKeys(ScriptDto scriptDto, Script script) {
        int index = 0;
        String[] keys = new String[5];
        keys[index++] = LockId.of(script.getId());
        keys[index++] = LockId.of(script.getName());
        keys[index++] = LockId.of(scriptDto.getName());
        keys[index++] = LockId.of(script.getProjectId());
        keys[index++] = LockId.of(scriptDto.getErrorEventHandler());

        return keys;
    }

    private void checkName(ProjectId projectId, ScriptName name, ScriptName newName) {
        if (newName.equals(name))
            return;

        if (scriptRepositoryGateway.find(projectId, newName).isPresent())
            throw new RuntimeException(String.format("Script \"%s\" already exists in Project \"%s\"", newName.getValue(), projectId.getValue())); // +
    }

    private void checkErrorHandler(Script script, ScriptId errorHandler) {
        if (errorHandler.isEmpty())
            return;

        if (script.getId().equals(errorHandler))
            throw new IllegalArgumentException("Script and ErrorHandler cannot have the same Id"); // +

        Optional<Script> errorHandlerScriptOpt = scriptRepositoryGateway.find(errorHandler);
        Script errorHandlerScript = errorHandlerScriptOpt.orElseThrow(() -> new ScriptNotFoundException(errorHandler));

        if (!script.getProjectId().equals(errorHandlerScript.getProjectId()))
            throw new RuntimeException(String.format("ErrorHandler \"%s\" was not found in Project \"%s\"", errorHandler.getValue(), script.getProjectId().toString())); // +
    }

}

