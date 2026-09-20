package gnoolson.saturday.script.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ScriptUpdatedEvent;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.script.model.entity.Script;
import gnoolson.saturday.script.model.exception.ScriptNotFoundException;
import gnoolson.saturday.script.model.vo.Code;
import gnoolson.saturday.script.port.inbound.UpdateCodeUseCase;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class UpdateCodeUseCaseImpl implements UpdateCodeUseCase {

    private final TransactionStarter transactionStarter;
    private final Locker locker;
    private final ScriptRepositoryGateway scriptRepositoryGateway;
    private final EventBus eventBus;

    /*
     *
     *
     * */
    @Override
    public void execute(ScriptId scriptId, Code code, List<ScriptId> includedScripts) {
        Script script = scriptRepositoryGateway.find(scriptId).orElseThrow(() -> new ScriptNotFoundException(scriptId));
        try (Locker.LockHandle ignore = locker.lockIds(generateKeys(script, includedScripts))) {
            checkIncludedScripts(script.getProjectId(), script.getId(), includedScripts);
            script.update(code, includedScripts);

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
    private String[] generateKeys(Script script, List<ScriptId> includedScripts) {
        int index = 0;
        String[] keys = new String[2 + includedScripts.size()];

        for (ScriptId includedScriptId : includedScripts) {
            keys[index++] = LockId.of(includedScriptId);
        }

        keys[index++] = LockId.of(script.getId());
        keys[index++] = LockId.of(script.getProjectId());

        return keys;
    }

    private void checkIncludedScripts(ProjectId projectId, ScriptId ownerScriptId, List<ScriptId> scripts) {
        for (ScriptId scriptId : scripts) {
            if (ownerScriptId.equals(scriptId))
                throw new IllegalArgumentException("Script cannot include itself"); // +

            scriptRepositoryGateway.find(projectId, scriptId).orElseThrow(() -> new ScriptNotFoundException(scriptId));
        }
    }

}
