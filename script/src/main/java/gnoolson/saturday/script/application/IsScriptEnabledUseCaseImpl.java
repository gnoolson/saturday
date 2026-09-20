package gnoolson.saturday.script.application;

import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.script.model.exception.ScriptNotFoundException;
import gnoolson.saturday.script.port.inbound.IsScriptEnabledUseCase;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsScriptEnabledUseCaseImpl implements IsScriptEnabledUseCase {

    private final ScriptRepositoryGateway scriptRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public boolean execute(ScriptId scriptId) {
        return scriptRepositoryGateway.find(scriptId).orElseThrow(() -> new ScriptNotFoundException(scriptId)).isEnabled();
    }

}
