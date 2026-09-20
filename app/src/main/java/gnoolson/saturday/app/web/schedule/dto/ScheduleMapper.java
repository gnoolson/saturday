package gnoolson.saturday.app.web.schedule.dto;

import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.schedule.model.vo.CronExpression;
import gnoolson.saturday.schedule.port.inbound.CreateScheduleUseCase;
import gnoolson.saturday.schedule.port.inbound.GetAllSchedulesUseCase;
import gnoolson.saturday.schedule.port.inbound.GetScheduleUseCase;
import gnoolson.saturday.schedule.port.inbound.UpdateScheduleUseCase;

public class ScheduleMapper {

    public static ScheduleDto toDto(GetAllSchedulesUseCase.ScheduleDto scheduleDto) {
        return new ScheduleDto(scheduleDto);
    }

    public static CreateScheduleUseCase.ScheduleDto toDomainDtoForCreate(ScheduleDto scheduleDto) {
        return new CreateScheduleUseCase.ScheduleDto(
                ProjectId.of(scheduleDto.getProjectId()),
                ScheduleName.of(scheduleDto.getName()),
                CronExpression.of(scheduleDto.getCronExpression()),
                Description.of(scheduleDto.getDescription()),
                ScriptId.of(scheduleDto.getScriptId())
        );
    }

    public static UpdateScheduleUseCase.ScheduleDto toDomainDtoForUpdate(ScheduleDto scheduleDto) {
        return new UpdateScheduleUseCase.ScheduleDto(
                ScheduleId.of(scheduleDto.getId()),
                ProjectId.of(scheduleDto.getProjectId()),
                ScheduleName.of(scheduleDto.getName()),
                CronExpression.of(scheduleDto.getCronExpression()),
                Description.of(scheduleDto.getDescription()),
                ScriptId.of(scheduleDto.getScriptId())
        );
    }

    public static ScheduleDto toDto(GetScheduleUseCase.ScheduleDto domainDto) {
        return new ScheduleDto(domainDto);
    }

}
