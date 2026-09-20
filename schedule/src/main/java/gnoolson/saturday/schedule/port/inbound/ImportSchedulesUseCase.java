package gnoolson.saturday.schedule.port.inbound;

import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.schedule.model.vo.CronExpression;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


public interface ImportSchedulesUseCase {

    void execute(List<ScheduleDto> schedules);

    @RequiredArgsConstructor
    @Getter
    class ScheduleDto {
        private final ScheduleId id;
        private final ProjectId projectId;
        private final ScheduleName name;
        private final Description description;
        private final CronExpression cronExpression;
        private final ScriptId scriptId;
    }

}
