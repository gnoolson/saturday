package gnoolson.saturday.schedule.application;

import gnoolson.saturday.schedule.model.entity.Schedule;
import gnoolson.saturday.schedule.port.inbound.GetAllSchedulesUseCase;
import gnoolson.saturday.schedule.port.outbound.ScheduleRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
public class GetAllSchedulesUseCaseImpl implements GetAllSchedulesUseCase {

    private final ScheduleRepositoryGateway scheduleRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public List<ScheduleDto> execute() {
        List<Schedule> schedules = scheduleRepositoryGateway.findAll();

        return schedules.stream().map(schedule -> {
            return new ScheduleDto(
                    schedule.getId(),
                    schedule.getProjectId(),
                    schedule.getName(),
                    schedule.getCronExpression(),
                    schedule.getDescription(),
                    schedule.getScriptId(),
                    schedule.isEnabled()
            );
        }).collect(Collectors.toList());
    }

}
