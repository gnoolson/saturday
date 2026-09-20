package gnoolson.saturday.script.application;

import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.script.port.inbound.IsDebugEnabledUseCase;
import gnoolson.saturday.script.port.outbound.IsDebugEnabledGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsDebugEnabledUseCaseImpl implements IsDebugEnabledUseCase {

    private final IsDebugEnabledGateway isDebugEnabledGateway;

    /*
     *
     *
     * */
    @Override
    public boolean execute(ScriptId id) {
        return isDebugEnabledGateway.execute(id);
    }

}
