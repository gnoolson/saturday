package gnoolson.saturday.schedule.port.inbound;

import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.schedule.model.vo.CronExpression;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


public interface GetAllSchedulesUseCase {

    List<ScheduleDto> execute();

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
