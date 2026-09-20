package gnoolson.saturday.script.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.eventbus.events.ScriptUpdatedEvent;
import gnoolson.saturday.common.eventbus.local.LocalEventBus;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.script.model.exception.ScriptNotFoundException;
import gnoolson.saturday.script.port.inbound.DeleteScriptUseCase;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class DeleteScriptUseCaseImpl implements DeleteScriptUseCase {

    private final ScriptRepositoryGateway scriptRepositoryGateway;
    private final TransactionStarter transactionStarter;
    private final LocalEventBus localEventBus;
    private final Locker locker;

    /*
     *
     *
     * */
    @Override
    public boolean execute(ScriptId id) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(id))) {
            transactionStarter.doIt(() -> {
                scriptRepositoryGateway.find(id).orElseThrow(() -> new ScriptNotFoundException(id));
                scriptRepositoryGateway.delete(id);

                transactionStarter.doAfterTransactionCommit(() -> {
                    localEventBus.emit(new ScriptUpdatedEvent(id));
                });
            });

            return true;
        } catch (Exception e) {
            log.warn("Exception", e);
            return false;
        }
    }

}
