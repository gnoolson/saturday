package gnoolson.saturday.app.web.schedule.dto;

import gnoolson.saturday.app.web.dto.NotEmptyUUIDValidation;
import gnoolson.saturday.app.web.schedule.dto.validation.CronExpressionValidation;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScheduleName;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.schedule.port.inbound.GetAllSchedulesUseCase;
import gnoolson.saturday.schedule.port.inbound.GetScheduleUseCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@Data
public class ScheduleDto {

    private UUID id;

    @NotBlank
    @Length(min = 1, max = 64)
    private String name;

    @NotNull
    @NotEmptyUUIDValidation(message = "{schedule.upsert.form.project_id.error}")
    private UUID projectId;

    @Length(max = 1000)
    private String description;

    @NotNull
    @NotEmptyUUIDValidation(message = "{schedule.upsert.form.script_id.error}")
    private UUID scriptId;

    private boolean enabled;

    @CronExpressionValidation(message = "{schedule.upsert.form.cron_expression.error}")
    private String cronExpression;

    /*
     *
     *
     * */
    public ScheduleDto(GetAllSchedulesUseCase.ScheduleDto scheduleDto) {
        this.id = scheduleDto.getId().getValue();
        this.name = scheduleDto.getName().getValue();
        this.description = scheduleDto.getDescription().getValue();
        this.scriptId = scheduleDto.getScriptId().getValue();
        this.enabled = scheduleDto.isEnabled();
        this.cronExpression = scheduleDto.getCronExpression().getValue();
        this.projectId = scheduleDto.getProjectId().getValue();
    }

    public ScheduleDto() {
        this.id = ScriptId.empty().getValue();
        this.name = ScheduleName.generate().getValue();
        this.description = StringUtils.EMPTY;
        this.cronExpression = "* * * * * *";
        this.scriptId = ScriptId.empty().getValue();
        this.projectId = ProjectId.empty().getValue();
    }

    public ScheduleDto(GetScheduleUseCase.ScheduleDto scheduleDto) {
        this.id = scheduleDto.getId().getValue();
        this.name = scheduleDto.getName().getValue();
        this.description = scheduleDto.getDescription().getValue();
        this.scriptId = scheduleDto.getScriptId().getValue();
        this.enabled = scheduleDto.isEnabled();
        this.cronExpression = scheduleDto.getCronExpression().getValue();
        this.projectId = scheduleDto.getProjectId().getValue();
    }

}
