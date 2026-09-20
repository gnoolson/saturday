package gnoolson.saturday.schedule.port.inbound;

import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.schedule.model.vo.CronExpression;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public interface GetScheduleUseCase {

    ScheduleDto execute(ScheduleId id);

    @RequiredArgsConstructor
    @Getter
    class ScheduleDto {
        private final ScheduleId id;
        private final ProjectId projectId;
        private final ScheduleName name;
        private final CronExpression cronExpression;
        private final Description description;
        private final ScriptId scriptId;
        private final boolean enabled;
    }

}
