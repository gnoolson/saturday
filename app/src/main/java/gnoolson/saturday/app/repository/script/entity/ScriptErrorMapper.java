package gnoolson.saturday.app.repository.script.entity;

import gnoolson.saturday.common.model.vo.ScriptErrorId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.time.vo.TimeInMs;
import gnoolson.saturday.script.model.entity.ScriptError;
import gnoolson.saturday.script.model.vo.ReasonOfError;
import gnoolson.saturday.script.model.vo.Stack;

import java.util.List;
import java.util.stream.Collectors;

public class ScriptErrorMapper {

    public static ScriptErrorEntity toJPA(ScriptError scriptError) {
        ScriptErrorEntity scriptErrorEntity = new ScriptErrorEntity();
        scriptErrorEntity.setId(scriptError.getId().isEmpty() ? null : scriptError.getId().getValue());
        scriptErrorEntity.setReasonOfError(scriptError.getReasonOfError().getValue());
        scriptErrorEntity.setScript(new ScriptEntity(scriptError.getScriptId().getValue()));
        scriptErrorEntity.setTime(scriptError.getTime().getValue());
        scriptErrorEntity.setStack(scriptError.getStack().getValue());

        return scriptErrorEntity;
    }

    public static List<ScriptError> toDomain(List<ScriptErrorEntity> entities) {
        return entities.stream().map(ScriptErrorMapper::toDomain).collect(Collectors.toList());
    }

    public static ScriptError toDomain(ScriptErrorEntity entity) {
        return new ScriptError(
                ScriptErrorId.of(entity.getId()),
                ScriptId.of(entity.getScript().getId()),
                TimeInMs.of(entity.getTime()),
                Stack.of(entity.getStack()),
                ReasonOfError.of(entity.getReasonOfError())
        );
    }

}
