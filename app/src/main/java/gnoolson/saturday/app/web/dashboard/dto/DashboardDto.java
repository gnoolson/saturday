package gnoolson.saturday.app.web.dashboard.dto;

import gnoolson.saturday.app.web.dto.NotEmptyUUIDValidation;
import gnoolson.saturday.common.model.vo.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DashboardDto {

    private UUID id;

    @NotBlank
    @Length(min = 1, max = 64)
    private String name;

    @NotNull
    @NotEmptyUUIDValidation(message = "{error.project_id}")
    private UUID projectId;

    @Length(max = 5000)
    private String description;

    private String html;

    @NotNull
    @NotEmptyUUIDValidation(message = "{error.script_id}")
    private UUID scriptId;

    @NotBlank
    @NotNull
    private String access;

    /*
     *
     *
     * */
    public DashboardDto(String defaultTemplate) {
        this.id = DashboardId.empty().getValue();
        this.name = DashboardName.generate().getValue();
        this.description = StringUtils.EMPTY;
        this.html = defaultTemplate;
        this.scriptId = ScriptId.empty().getValue();
        this.projectId = ProjectId.empty().getValue();
        this.access = Access.SUPER.name();
    }

}
