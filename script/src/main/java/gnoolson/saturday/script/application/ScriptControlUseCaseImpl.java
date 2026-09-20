package gnoolson.saturday.script.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ScriptUpdatedEvent;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.script.model.entity.Script;
import gnoolson.saturday.script.model.exception.ScriptNotFoundException;
import gnoolson.saturday.script.port.inbound.ScriptControlUseCase;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class ScriptControlUseCaseImpl implements ScriptControlUseCase {

    private final ScriptRepositoryGateway scriptRepositoryGateway;
    private final TransactionStarter transactionStarter;
    private final Locker locker;
    private final EventBus eventBus;

    /*
     *
     *
     * */
    @Override
    public void execute(ScriptId id, boolean flag) {
        Script script = scriptRepositoryGateway.find(id).orElseThrow(() -> new ScriptNotFoundException(id));

        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(id))) {
            if (script.isBlocked())
                throw new IllegalStateException("Script is blocked"); // +

            transactionStarter.doIt(() -> {

                if (flag)
                    script.enable();
                else
                    script.disable();

                transactionStarter.doAfterTransactionCommit(() -> {
                    eventBus.emit(new ScriptUpdatedEvent(script.getId()));
                });
                scriptRepositoryGateway.save(script);
            });
        }
    }

}
