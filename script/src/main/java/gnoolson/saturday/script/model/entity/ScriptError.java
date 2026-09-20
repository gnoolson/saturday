package gnoolson.saturday.script.model.entity;

import gnoolson.saturday.common.model.vo.ScriptErrorId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.time.vo.TimeInMs;
import gnoolson.saturday.common.validator.DomainModelValidator;
import gnoolson.saturday.script.model.vo.ReasonOfError;
import gnoolson.saturday.script.model.vo.Stack;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class ScriptError {

    private final ScriptErrorId id;
    private final ScriptId scriptId;
    private final TimeInMs time;
    private final Stack stack;
    private final ReasonOfError reasonOfError;

    public ScriptError(ScriptErrorId id, ScriptId scriptId, TimeInMs time, Stack stack, ReasonOfError reasonOfError) {
        DomainModelValidator.checkNotNull(id, "ScriptErrorId");
        DomainModelValidator.checkNotNull(scriptId, "ScriptId");
        DomainModelValidator.checkNotNull(time, "Time");
        DomainModelValidator.checkNotNull(stack, "Stack");
        DomainModelValidator.checkNotNull(reasonOfError, "ReasonOfError");

        this.id = id;
        this.scriptId = scriptId;
        this.time = time;
        this.stack = stack;
        this.reasonOfError = reasonOfError;
    }


}
