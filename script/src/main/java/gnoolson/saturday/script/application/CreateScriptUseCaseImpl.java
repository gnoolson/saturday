package gnoolson.saturday.script.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.ScriptName;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.script.model.entity.Script;
import gnoolson.saturday.script.model.exception.ScriptNotFoundException;
import gnoolson.saturday.script.port.inbound.CreateScriptUseCase;
import gnoolson.saturday.script.port.outbound.ProjectIdCheckerGateway;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
@RequiredArgsConstructor
public class CreateScriptUseCaseImpl implements CreateScriptUseCase {

    private final ScriptRepositoryGateway scriptRepositoryGateway;
    private final TransactionStarter transactionStarter;
    private final Locker locker;
    private final ProjectIdCheckerGateway projectIdCheckerGateway;

    /*
     *
     *
     * */
    @Override
    public ScriptId execute(ScriptDto scriptDto) {
        String[] keys = generateKeys(scriptDto);

        try (Locker.LockHandle ignore = locker.lockIds(keys)) {
            checkProject(scriptDto.getProjectId());
            checkName(scriptDto.getProjectId(), scriptDto.getName());
            checkErrorHandler(scriptDto.getProjectId(), scriptDto.getErrorEventHandler());

            Script script = new Script(
                    ScriptId.empty(),
                    scriptDto.getProjectId(),
                    scriptDto.getName(),
                    scriptDto.getDescription(),
                    scriptDto.getCode(),
                    Collections.emptyList(),
                    scriptDto.getCachingTime(),
                    scriptDto.isAutostart(),
                    scriptDto.getErrorEventHandler()
            );

            AtomicReference<ScriptId> result = new AtomicReference<>();
            transactionStarter.doIt(() -> {
                ScriptId id = scriptRepositoryGateway.save(script);
                result.set(id);
            });

            return result.get();
        }

    }

    /*
     *
     *
     * */
    private void checkProject(ProjectId projectId) {
        if (!projectIdCheckerGateway.exists(projectId))
            throw new RuntimeException(String.format("Project \"%s\" was not found", projectId.getValue().toString())); // +
    }

    private void checkName(ProjectId projectId, ScriptName name) {
        if (scriptRepositoryGateway.find(projectId, name).isPresent())
            throw new RuntimeException(String.format("Script \"%s\" already exits in Project \"%s\"", name.getValue(), projectId.getValue())); // +
    }

    private String[] generateKeys(ScriptDto scriptDto) {
        int index = 0;
        String[] keys = new String[3];
        keys[index++] = LockId.of(scriptDto.getName());
        keys[index++] = LockId.of(scriptDto.getProjectId());
        keys[index++] = LockId.of(scriptDto.getErrorEventHandler());

        return keys;
    }

    private void checkErrorHandler(ProjectId projectId, ScriptId errorHandler) {
        if (errorHandler.isEmpty())
            return;

        Optional<Script> errorHandlerScriptOpt = scriptRepositoryGateway.find(errorHandler);
        Script errorHandlerScript = errorHandlerScriptOpt.orElseThrow(() -> new ScriptNotFoundException(errorHandler));

        if (!projectId.equals(errorHandlerScript.getProjectId()))
            throw new RuntimeException(String.format("ErrorHandler \"%s\" was not found in Project \"%s\"", errorHandler.getValue(), projectId.getValue())); // +
    }

}
