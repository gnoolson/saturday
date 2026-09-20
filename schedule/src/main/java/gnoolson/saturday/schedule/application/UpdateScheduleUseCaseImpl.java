package gnoolson.saturday.schedule.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ScheduleUpdatedEvent;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScheduleName;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.schedule.model.entity.Schedule;
import gnoolson.saturday.schedule.model.exception.ScheduleNotFoundException;
import gnoolson.saturday.schedule.port.inbound.UpdateScheduleUseCase;
import gnoolson.saturday.schedule.port.outbound.ScheduleRepositoryGateway;
import gnoolson.saturday.schedule.port.outbound.ScriptIdCheckerGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class UpdateScheduleUseCaseImpl implements UpdateScheduleUseCase {

    private final TransactionStarter transactionStarter;
    private final Locker locker;
    private final ScheduleRepositoryGateway scheduleRepositoryGateway;
    private final EventBus eventBus;
    private final ScriptIdCheckerGateway scriptIdCheckerGateway;

    /*
     *
     *
     * */
    @Override
    public void execute(ScheduleDto scheduleDto) {
        Schedule schedule = scheduleRepositoryGateway.find(scheduleDto.getId()).orElseThrow(() -> new ScheduleNotFoundException(scheduleDto.getId()));

        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(scheduleDto.getId()),
                LockId.of(scheduleDto.getScriptId()),
                LockId.of(schedule.getName()),
                LockId.of(scheduleDto.getName()))) {

            checkName(schedule.getProjectId(), schedule.getName(), scheduleDto.getName());
            checkScript(schedule.getProjectId(), scheduleDto.getScriptId());

            schedule.update(scheduleDto.getName(), scheduleDto.getDescription(), scheduleDto.getCronExpression(), scheduleDto.getScriptId());

            transactionStarter.doIt(() -> {
                transactionStarter.doAfterTransactionCommit(() -> {
                    eventBus.emit(new ScheduleUpdatedEvent(schedule.getId()));
                });
                scheduleRepositoryGateway.save(schedule);
            });
        }
    }

    /*
     *
     *
     * */
    private void checkScript(ProjectId projectId, ScriptId scriptId) {
        if (!scriptIdCheckerGateway.exists(projectId, scriptId))
            throw new RuntimeException(String.format("Script \"%s\" was not found", scriptId.getValue().toString())); // +
    }

    private void checkName(ProjectId projectId, ScheduleName name, ScheduleName newName) {
        if (name.equals(newName))
            return;

        if (scheduleRepositoryGateway.find(projectId, newName).isPresent())
            throw new RuntimeException(String.format("Schedule \"%s\" already exists in Project \"%s\"", newName.getValue(), projectId.getValue())); // +
    }

}
