package gnoolson.saturday.app.web.script.dto;

import gnoolson.saturday.app.web.dto.NotEmptyUUIDValidation;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.ScriptName;
import gnoolson.saturday.script.model.vo.Code;
import gnoolson.saturday.script.port.inbound.GetAllScriptsUseCase;
import gnoolson.saturday.script.port.inbound.GetScriptUseCase;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
@Getter
public class ScriptDto {

    private UUID id;

    @NotBlank
    @Length(min = 1, max = 64)
    private String name;

    @NotNull
    @NotEmptyUUIDValidation(message = "{script.upsert.form.project_id.error}")
    private UUID projectId;

    @Length(max = 5000)
    private String description;

    private String code;

    private boolean enabled;
    private boolean blocked;
    private boolean autostart;
    private int errors;

    private List<UUID> includedScripts;

    @Min(0)
    @Max(100_000)
    private int cachingTime;

    @NotNull
    private UUID errorEventHandler;

    /*
     *
     *
     * */
    public ScriptDto(Code code) {
        this.id = ScriptId.empty().getValue();
        this.projectId = ProjectId.empty().getValue();
        this.name = ScriptName.generate().getValue();
        this.code = code.getValue();
        this.description = StringUtils.EMPTY;
        this.includedScripts = new ArrayList<>(0);
        this.cachingTime = 5 * 60; // 5m
        this.autostart = false;
        this.errorEventHandler = ScriptId.empty().getValue();
    }

    public ScriptDto(GetScriptUseCase.ScriptDto script) {
        this.id = script.getId().getValue();
        this.projectId = script.getProjectId().getValue();
        this.name = script.getName().getValue();
        this.description = script.getDescription().getValue();
        this.enabled = script.isEnabled();
        this.code = script.getCode().getValue();
        this.blocked = script.isBlocked();
        this.includedScripts = script.getIncludedScripts().stream().map(ScriptId::getValue).collect(Collectors.toList());
        this.cachingTime = script.getCachingTime().getValue();
        this.autostart = script.isAutostart();
        this.errorEventHandler = script.getErrorEventHandler().getValue();
    }

    public ScriptDto(GetAllScriptsUseCase.ScriptDto script) {
        this.id = script.getId().getValue();
        this.projectId = script.getProjectId().getValue();
        this.name = script.getName().getValue();
        this.description = script.getDescription().getValue();
        this.enabled = script.isEnabled();
        this.blocked = script.isBlocked();
        this.errors = script.getErrors().getValue();
        this.includedScripts = new ArrayList<>(0);
        this.cachingTime = 0;
        this.autostart = script.isAutostart();
        this.errorEventHandler = ScriptId.empty().getValue();
    }

}
