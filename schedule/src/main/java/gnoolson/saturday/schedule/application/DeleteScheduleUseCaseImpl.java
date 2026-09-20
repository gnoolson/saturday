package gnoolson.saturday.schedule.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ScheduleUpdatedEvent;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ScheduleId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.schedule.model.exception.ScheduleNotFoundException;
import gnoolson.saturday.schedule.port.inbound.DeleteScheduleUseCase;
import gnoolson.saturday.schedule.port.outbound.ScheduleRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class DeleteScheduleUseCaseImpl implements DeleteScheduleUseCase {

    private final TransactionStarter transactionStarter;
    private final Locker locker;
    private final ScheduleRepositoryGateway scheduleRepositoryGateway;
    private final EventBus eventBus;

    /*
     *
     *
     * */
    @Override
    public boolean execute(ScheduleId id) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(id))) {
            transactionStarter.doIt(() -> {
                transactionStarter.doAfterTransactionCommit(() -> {
                    eventBus.emit(new ScheduleUpdatedEvent(id));
                });

                scheduleRepositoryGateway.find(id).orElseThrow(() -> new ScheduleNotFoundException(id));
                scheduleRepositoryGateway.delete(id);
            });

            return true;
        } catch (Exception e) {
            log.warn("Exception", e);
            return false;
        }
    }

}
