package gnoolson.saturday.app.repository.schedule.entity;

import gnoolson.saturday.app.repository.project.entity.ProjectEntity;
import gnoolson.saturday.app.repository.script.entity.ScriptEntity;
import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.schedule.model.entity.Schedule;
import gnoolson.saturday.schedule.model.vo.CronExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScheduleMapper {

    public static Schedule toDomain(ScheduleEntity scheduleEntity) {
        return new Schedule(
                ScheduleId.of(scheduleEntity.getId()),
                ProjectId.of(scheduleEntity.getProject().getId()),
                ScheduleName.of(scheduleEntity.getName()),
                CronExpression.of(scheduleEntity.getCronExpression()),
                Description.of(scheduleEntity.getDescription()),
                ScriptId.of(scheduleEntity.getScript().getId()),
                scheduleEntity.isEnabled()
        );
    }

    public static ScheduleEntity toJPA(Schedule schedule) {
        return new ScheduleEntity(
                schedule.getId().isEmpty() ? UUID.randomUUID() : schedule.getId().getValue(),
                new ProjectEntity(schedule.getProjectId().getValue()),
                schedule.getName().getValue(),
                schedule.getDescription().getValue(),
                schedule.getCronExpression().getValue(),
                schedule.isEnabled(),
                new ScriptEntity(schedule.getScriptId().getValue())
        );
    }

    public static List<Schedule> toDomain(Iterable<ScheduleEntity> scheduleEntities) {
        List<Schedule> result = new ArrayList<>();
        for (ScheduleEntity scheduleEntity : scheduleEntities) {
            result.add(toDomain(scheduleEntity));
        }
        return result;
    }

}
