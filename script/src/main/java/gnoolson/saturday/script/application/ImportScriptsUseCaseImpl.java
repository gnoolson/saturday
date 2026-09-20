package gnoolson.saturday.script.application;

import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ScriptUpdatedEvent;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.ScriptName;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.script.model.entity.Script;
import gnoolson.saturday.script.port.inbound.ImportScriptsUseCase;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ImportScriptsUseCaseImpl implements ImportScriptsUseCase {

    private final ScriptRepositoryGateway scriptRepositoryGateway;
    private final TransactionStarter transactionStarter;
    private final EventBus eventBus;

    /*
     *
     *
     * */
    @Override
    public void execute(List<ScriptDto> scripts) {
        for (ScriptDto script : scripts) {
            save(script);
        }
    }

    /*
     *
     *
     * */
    private void save(ScriptDto scriptDto) {
        checkId(scriptDto.getId());
        checkName(scriptDto.getProjectId(), scriptDto.getName());

        Script script = new Script(
                scriptDto.getId(),
                scriptDto.getProjectId(),
                scriptDto.getName(),
                scriptDto.getDescription(),
                scriptDto.getCode(),
                scriptDto.getIncludedScripts(),
                scriptDto.getCachingTime(),
                scriptDto.isAutostart(),
                scriptDto.getErrorEventHandler()
        );

        transactionStarter.doAfterTransactionCommit(() -> {
            eventBus.emit(new ScriptUpdatedEvent(script.getId()));
        });
        scriptRepositoryGateway.save(script);
    }

    private void checkName(ProjectId projectId, ScriptName name) {
        if (scriptRepositoryGateway.find(projectId, name).isPresent())
            throw new RuntimeException(String.format("Script \"%s\" already exists in Project \"%s\"", name.getValue(), projectId.getValue().toString())); // +
    }

    private void checkId(ScriptId id) {
        if (idExists(id))
            throw new RuntimeException(String.format("Script \"%s\" already exists", id.getValue().toString())); // +
    }

    private boolean idExists(ScriptId id) {
        return scriptRepositoryGateway.find(id).isPresent();
    }

}
