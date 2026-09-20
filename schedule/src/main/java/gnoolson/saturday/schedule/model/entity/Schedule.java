package gnoolson.saturday.schedule.model.entity;

import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.common.validator.DomainModelValidator;
import gnoolson.saturday.schedule.model.vo.CronExpression;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Schedule {

    private final ScheduleId id;
    private final ProjectId projectId;
    private ScheduleName name;
    private CronExpression cronExpression;
    private Description description;
    private ScriptId scriptId;
    private boolean enabled;

    /*
     *
     *
     * */
    public Schedule(ScheduleId id, ProjectId projectId, ScheduleName name, CronExpression cronExpression, Description description, ScriptId scriptId, boolean enabled) {
        DomainModelValidator.checkNotNull(id, "ScheduleId");
        DomainModelValidator.checkNotNull(projectId, "ProjectId");
        DomainModelValidator.checkNotNull(name, "ScheduleName");
        DomainModelValidator.checkNotNull(description, "Description");
        DomainModelValidator.checkNotNull(scriptId, "ScriptId");
        DomainModelValidator.checkNotNull(scriptId, "CronExpression");

        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.cronExpression = cronExpression;
        this.description = description;
        this.scriptId = scriptId;
        this.enabled = enabled;
    }

    public void update(ScheduleName name, Description description, CronExpression cronExpression, ScriptId scriptId) {
        DomainModelValidator.checkNotNull(name, "ScheduleName");
        DomainModelValidator.checkNotNull(description, "Description");
        DomainModelValidator.checkNotNull(scriptId, "ScriptId");
        DomainModelValidator.checkNotNull(scriptId, "CronExpression");

        this.name = name;
        this.cronExpression = cronExpression;
        this.description = description;
        this.scriptId = scriptId;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disabled() {
        this.enabled = false;
    }

}


