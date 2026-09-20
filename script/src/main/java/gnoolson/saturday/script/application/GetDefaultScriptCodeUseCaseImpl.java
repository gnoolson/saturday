package gnoolson.saturday.script.application;

import gnoolson.saturday.script.model.vo.Code;
import gnoolson.saturday.script.port.inbound.GetDefaultScriptCodeUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetDefaultScriptCodeUseCaseImpl implements GetDefaultScriptCodeUseCase {

    private final Code code;

    @Override
    public Code execute() {
        return code;
    }

}
