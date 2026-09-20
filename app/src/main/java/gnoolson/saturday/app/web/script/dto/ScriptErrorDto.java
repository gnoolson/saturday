package gnoolson.saturday.app.web.script.dto;

import gnoolson.saturday.script.port.inbound.GetNumberOfErrorsUseCase;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ScriptErrorDto {

    private final long time;
    private final String reason;
    private final String stack;

    public ScriptErrorDto(GetNumberOfErrorsUseCase.ScriptErrorDto scriptError) {
        this.time = scriptError.getTime().getValue();
        this.reason = scriptError.getReasonOfError().getValue();
        this.stack = scriptError.getStack().getValue();
    }

}
