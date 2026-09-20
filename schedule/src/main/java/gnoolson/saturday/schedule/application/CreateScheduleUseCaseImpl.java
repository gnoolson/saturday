package gnoolson.saturday.schedule.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScheduleId;
import gnoolson.saturday.common.model.vo.ScheduleName;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.schedule.model.entity.Schedule;
import gnoolson.saturday.schedule.port.inbound.CreateScheduleUseCase;
import gnoolson.saturday.schedule.port.outbound.ProjectIdCheckerGateway;
import gnoolson.saturday.schedule.port.outbound.ScheduleRepositoryGateway;
import gnoolson.saturday.schedule.port.outbound.ScriptIdCheckerGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.atomic.AtomicReference;

@Log4j2
@RequiredArgsConstructor
public class CreateScheduleUseCaseImpl implements CreateScheduleUseCase {

    private final TransactionStarter transactionStarter;
    private final Locker locker;
    private final ScheduleRepositoryGateway scheduleRepositoryGateway;
    private final ScriptIdCheckerGateway scriptIdCheckerGateway;
    private final ProjectIdCheckerGateway projectIdCheckerGateway;

    /*
     *
     *
     * */
    @Override
    public ScheduleId execute(ScheduleDto scheduleDto) {
        try (Locker.LockHandle ignore = locker.lockIds(
                LockId.of(scheduleDto.getName()),
                LockId.of(scheduleDto.getScriptId()),
                LockId.of(scheduleDto.getProjectId()))) {

            checkProject(scheduleDto.getProjectId());
            checkName(scheduleDto.getProjectId(), scheduleDto.getName());
            checkScript(scheduleDto.getProjectId(), scheduleDto.getScriptId());

            Schedule schedule = new Schedule(
                    ScheduleId.empty(),
                    scheduleDto.getProjectId(),
                    scheduleDto.getName(),
                    scheduleDto.getCronExpression(),
                    scheduleDto.getDescription(),
                    scheduleDto.getScriptId(),
                    false
            );

            AtomicReference<ScheduleId> result = new AtomicReference<>();
            transactionStarter.doIt(() -> {
                ScheduleId id = scheduleRepositoryGateway.save(schedule);
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

    private void checkName(ProjectId projectId, ScheduleName name) {
        if (scheduleRepositoryGateway.find(projectId, name).isPresent())
            throw new RuntimeException(String.format("Schedule \"%s\" already exists in Project \"%s\"", name.getValue(), projectId.getValue())); // +
    }

    private void checkScript(ProjectId projectId, ScriptId scriptId) {
        if (!scriptIdCheckerGateway.exists(projectId, scriptId))
            throw new RuntimeException(String.format("Script \"%s\" was not found", scriptId.getValue().toString())); // +
    }

}
