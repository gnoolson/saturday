package gnoolson.saturday.script.port.inbound;

import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.time.vo.TimeInMs;
import gnoolson.saturday.script.model.entity.ScriptError;
import gnoolson.saturday.script.model.vo.ReasonOfError;
import gnoolson.saturday.script.model.vo.Stack;
import lombok.Getter;

import java.util.List;


public interface GetNumberOfErrorsUseCase {

    List<ScriptErrorDto> execute(ScriptId scriptId);

    @Getter
    class ScriptErrorDto {
        private final TimeInMs time;
        private final Stack stack;
        private final ReasonOfError reasonOfError;

        public ScriptErrorDto(ScriptError scriptError) {
            this.time = scriptError.getTime();
            this.reasonOfError = scriptError.getReasonOfError();
            this.stack = scriptError.getStack();
        }

    }

}
