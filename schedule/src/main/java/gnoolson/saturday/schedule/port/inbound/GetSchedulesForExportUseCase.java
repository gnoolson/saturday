package gnoolson.saturday.schedule.port.inbound;

import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.schedule.model.vo.CronExpression;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;


public interface GetSchedulesForExportUseCase {

    List<ScheduleDto> execute(Set<ProjectId> projectIdSet);

    @RequiredArgsConstructor
    @Getter
    class ScheduleDto {
        private final ScheduleId id;
        private final ProjectId projectId;
        private final ScheduleName name;
        private final CronExpression cronExpression;
        private final Description description;
        private final ScriptId scriptId;
    }

}
