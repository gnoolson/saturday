package gnoolson.saturday.schedule.application;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.schedule.model.entity.Schedule;
import gnoolson.saturday.schedule.port.inbound.GetSchedulesForExportUseCase;
import gnoolson.saturday.schedule.port.outbound.ScheduleRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetSchedulesForExportUseCaseImpl implements GetSchedulesForExportUseCase {

    private final ScheduleRepositoryGateway scheduleRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public List<ScheduleDto> execute(Set<ProjectId> projectIdSet) {
        List<Schedule> schedules = scheduleRepositoryGateway.findAll();

        return schedules.stream()
                .filter(schedule -> {
                    return projectIdSet.contains(schedule.getProjectId());
                })
                .map(schedule -> {
                    return new ScheduleDto(
                            schedule.getId(),
                            schedule.getProjectId(),
                            schedule.getName(),
                            schedule.getCronExpression(),
                            schedule.getDescription(),
                            schedule.getScriptId()
                    );
                }).collect(Collectors.toList());
    }

}
