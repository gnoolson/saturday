package gnoolson.saturday.schedule.application;

import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ScheduleUpdatedEvent;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScheduleId;
import gnoolson.saturday.common.model.vo.ScheduleName;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.schedule.model.entity.Schedule;
import gnoolson.saturday.schedule.port.inbound.ImportSchedulesUseCase;
import gnoolson.saturday.schedule.port.outbound.ScheduleRepositoryGateway;
import gnoolson.saturday.schedule.port.outbound.ScriptIdCheckerGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ImportSchedulesUseCaseImpl implements ImportSchedulesUseCase {

    private final ScheduleRepositoryGateway scheduleRepositoryGateway;
    private final ScriptIdCheckerGateway scriptIdCheckerGateway;
    private final EventBus eventBus;
    private final TransactionStarter transactionStarter;

    /*
     *
     *
     * */
    @Override
    public void execute(List<ScheduleDto> schedules) {
        for (ScheduleDto schedule : schedules) {
            save(schedule);
        }
    }

    /*
     *
     *
     * */
    private void save(ScheduleDto scheduleDto) {
        checkId(scheduleDto.getId());
        checkName(scheduleDto.getProjectId(), scheduleDto.getName());
        checkScript(scheduleDto.getProjectId(), scheduleDto.getScriptId());

        Schedule schedule = new Schedule(
                scheduleDto.getId(),
                scheduleDto.getProjectId(),
                scheduleDto.getName(),
                scheduleDto.getCronExpression(),
                scheduleDto.getDescription(),
                scheduleDto.getScriptId(),
                false
        );

        transactionStarter.doAfterTransactionCommit(() -> {
            eventBus.emit(new ScheduleUpdatedEvent(schedule.getId()));
        });
        scheduleRepositoryGateway.save(schedule);
    }

    private void checkScript(ProjectId projectId, ScriptId scriptId) {
        if (!scriptIdCheckerGateway.exists(projectId, scriptId))
            throw new RuntimeException(String.format("Script \"%s\" was not found", scriptId.getValue().toString())); // +
    }

    private void checkName(ProjectId projectId, ScheduleName name) {
        if (scheduleRepositoryGateway.find(projectId, name).isPresent())
            throw new RuntimeException(String.format("Schedule \"%s\" already exists in Project \"%s\" ", name.getValue(), projectId.getValue().toString())); // +
    }

    private void checkId(ScheduleId id) {
        if (idExists(id))
            throw new RuntimeException(String.format("Schedule \"%s\" already exists", id.getValue().toString())); // +
    }

    private boolean idExists(ScheduleId id) {
        return scheduleRepositoryGateway.find(id).isPresent();
    }

}
