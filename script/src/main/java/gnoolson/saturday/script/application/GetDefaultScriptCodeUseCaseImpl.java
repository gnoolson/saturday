package gnoolson.saturday.script.application;

import gnoolson.saturday.script.model.vo.Code;
import gnoolson.saturday.script.port.inbound.GetDefaultScriptCodeUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetDefaultScriptCodeUseCaseImpl implements GetDefaultScriptCodeUseCase {

    private final CodeProvider codeProvider;

    /*
     *
     *
     * */
    @Override
    public Code execute() {
        return codeProvider.execute();
    }

}
