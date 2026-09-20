package gnoolson.saturday.app.web.script.dto;

import gnoolson.saturday.common.cache.CachingTime;
import gnoolson.saturday.common.model.vo.Description;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.ScriptName;
import gnoolson.saturday.script.model.vo.Code;
import gnoolson.saturday.script.port.inbound.CreateScriptUseCase;
import gnoolson.saturday.script.port.inbound.GetAllScriptsUseCase;
import gnoolson.saturday.script.port.inbound.UpdateScriptUseCase;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ScriptMapper {

    public static Collection<ScriptDto> toDto(List<GetAllScriptsUseCase.ScriptDto> scripts) {
        return scripts.stream().map(ScriptDto::new).collect(Collectors.toList());
    }

    public static CreateScriptUseCase.ScriptDto toDomainDtoForCreateUseCase(ScriptDto scriptDto) {
        return new CreateScriptUseCase.ScriptDto(
                ProjectId.of(scriptDto.getProjectId()),
                ScriptName.of(scriptDto.getName()),
                Description.of(scriptDto.getDescription()),
                CachingTime.of(scriptDto.getCachingTime()),
                scriptDto.isAutostart(),
                ScriptId.of(scriptDto.getErrorEventHandler()),
                Code.of(scriptDto.getCode())
        );
    }

    public static UpdateScriptUseCase.ScriptDto toDomainDtoForUpdateUseCase(ScriptDto scriptDto) {
        return new UpdateScriptUseCase.ScriptDto(
                ScriptId.of(scriptDto.getId()),
                ProjectId.of(scriptDto.getProjectId()),
                ScriptName.of(scriptDto.getName()),
                Description.of(scriptDto.getDescription()),
                CachingTime.of(scriptDto.getCachingTime()),
                scriptDto.isAutostart(),
                ScriptId.of(scriptDto.getErrorEventHandler())
        );
    }

}
