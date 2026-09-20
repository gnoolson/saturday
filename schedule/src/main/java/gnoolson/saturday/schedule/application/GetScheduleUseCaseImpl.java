package gnoolson.saturday.schedule.application;

import gnoolson.saturday.common.model.vo.ScheduleId;
import gnoolson.saturday.schedule.model.entity.Schedule;
import gnoolson.saturday.schedule.model.exception.ScheduleNotFoundException;
import gnoolson.saturday.schedule.port.inbound.GetScheduleUseCase;
import gnoolson.saturday.schedule.port.outbound.ScheduleRepositoryGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetScheduleUseCaseImpl implements GetScheduleUseCase {

    private final ScheduleRepositoryGateway scheduleRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public ScheduleDto execute(ScheduleId id) {
        Schedule schedule = scheduleRepositoryGateway.find(id).orElseThrow(() -> new ScheduleNotFoundException(id));

        return new ScheduleDto(
                schedule.getId(),
                schedule.getProjectId(),
                schedule.getName(),
                schedule.getCronExpression(),
                schedule.getDescription(),
                schedule.getScriptId(),
                schedule.isEnabled()
        );
    }

}
