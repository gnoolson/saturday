package gnoolson.saturday.app.repository.script.entity;

import gnoolson.saturday.app.repository.project.entity.ProjectEntity;
import gnoolson.saturday.common.cache.CachingTime;
import gnoolson.saturday.common.model.vo.Description;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.ScriptName;
import gnoolson.saturday.script.model.entity.Script;
import gnoolson.saturday.script.model.vo.Code;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class ScriptMapper {

    public static Script toDomain(ScriptEntity scriptEntity) {


        return new Script(
                ScriptId.of(scriptEntity.getId()),
                ProjectId.of(scriptEntity.getProject().getId()),
                ScriptName.of(scriptEntity.getName()),
                Description.of(scriptEntity.getDescription()),
                Code.of(scriptEntity.getCode()),
                scriptEntity.isEnabled(),
                scriptEntity.isBlocked(),
                scriptEntity.getIncludedScripts().stream().map(ScriptId::of).collect(Collectors.toList()),
                CachingTime.of(scriptEntity.getCachingTime()),
                scriptEntity.isAutostart(),
                ScriptId.of(scriptEntity.getErrorEventHandler())
        );
    }

    public static ScriptEntity toJPA(Script script) {
        ScriptEntity scriptEntity = new ScriptEntity();
        scriptEntity.setId(script.getId().isEmpty() ? UUID.randomUUID() : script.getId().getValue());
        scriptEntity.setProject(new ProjectEntity(script.getProjectId().getValue()));
        scriptEntity.setName(script.getName().getValue());
        scriptEntity.setDescription(script.getDescription().getValue());
        scriptEntity.setCode(script.getCode().getValue());
        scriptEntity.setEnabled(script.isEnabled());
        scriptEntity.setBlocked(script.isBlocked());
        scriptEntity.setIncludedScripts(script.getIncludedScripts().stream().map(ScriptId::getValue).collect(Collectors.toSet()));
        scriptEntity.setCachingTime(script.getCachingTime().getValue());
        scriptEntity.setAutostart(script.isAutostart());
        scriptEntity.setErrorEventHandler(script.getErrorEventHandler().getValue());

        return scriptEntity;
    }

    public static List<Script> toDomain(Iterable<ScriptEntity> scrips) {
        List<Script> result = new ArrayList<>();
        scrips.forEach((scriptEntity) -> {
            result.add(toDomain(scriptEntity));
        });

        return result;
    }

}
