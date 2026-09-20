package gnoolson.saturday.script.application;

import gnoolson.saturday.script.model.vo.Code;
import gnoolson.saturday.script.port.inbound.BeautifyCodeUseCase;
import gnoolson.saturday.script.port.outbound.BeautifyCodeGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BeautifyCodeUseCaseImpl implements BeautifyCodeUseCase {

    private final BeautifyCodeGateway beautifyCodeGateway;

    /*
     *
     *
     * */
    @Override
    public Code execute(Code code) {
        return beautifyCodeGateway.execute(code);
    }

}
