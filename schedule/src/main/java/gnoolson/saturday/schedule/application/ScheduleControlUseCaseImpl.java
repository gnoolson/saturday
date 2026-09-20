package gnoolson.saturday.schedule.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ScheduleUpdatedEvent;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ScheduleId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.schedule.model.entity.Schedule;
import gnoolson.saturday.schedule.model.exception.ScheduleNotFoundException;
import gnoolson.saturday.schedule.port.inbound.ScheduleControlUseCase;
import gnoolson.saturday.schedule.port.outbound.ScheduleRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class ScheduleControlUseCaseImpl implements ScheduleControlUseCase {

    private final TransactionStarter transactionStarter;
    private final Locker locker;
    private final ScheduleRepositoryGateway scheduleRepositoryGateway;
    private final EventBus eventBus;

    /*
     *
     *
     * */
    @Override
    public void execute(ScheduleId id, boolean flag) {
        Schedule schedule = scheduleRepositoryGateway.find(id).orElseThrow(() -> new ScheduleNotFoundException(id));

        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(id))) {
            transactionStarter.doIt(() -> {

                if (flag)
                    schedule.enable();
                else
                    schedule.disabled();

                transactionStarter.doAfterTransactionCommit(() -> {
                    eventBus.emit(new ScheduleUpdatedEvent(schedule.getId()));
                });

                scheduleRepositoryGateway.save(schedule);
            });
        }
    }

}
