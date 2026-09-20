package gnoolson.saturday.schedule.port.inbound;

import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.schedule.model.vo.CronExpression;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public interface CreateScheduleUseCase {

    ScheduleId execute(ScheduleDto scheduleDto);

    @RequiredArgsConstructor
    @Getter
    class ScheduleDto {
        private final ProjectId projectId;
        private final ScheduleName name;
        private final CronExpression cronExpression;
        private final Description description;
        private final ScriptId scriptId;
    }

}
