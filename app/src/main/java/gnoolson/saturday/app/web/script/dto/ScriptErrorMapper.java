package gnoolson.saturday.app.web.script.dto;

import gnoolson.saturday.script.port.inbound.GetNumberOfErrorsUseCase;

import java.util.List;
import java.util.stream.Collectors;

public class ScriptErrorMapper {


    public static List<ScriptErrorDto> toDto(List<GetNumberOfErrorsUseCase.ScriptErrorDto> scriptErrors) {
        return scriptErrors.stream().map(ScriptErrorDto::new).collect(Collectors.toList());
    }

}
