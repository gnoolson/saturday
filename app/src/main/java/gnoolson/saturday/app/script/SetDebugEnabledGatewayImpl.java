package gnoolson.saturday.app.script;

import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.internal_lua_libs.log.LogGateway;
import gnoolson.saturday.script.port.outbound.SetDebugEnabledGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SetDebugEnabledGatewayImpl implements SetDebugEnabledGateway {

    private final gnoolson.saturday.common.cache.Cache<ScriptId, LogGateway> logCache;

    /*
     *
     *
     * */
    @Override
    public void execute(ScriptId scriptId, boolean flag) {
        LogGateway logGateway = logCache.getAndProlongOrCreate(scriptId);
        logGateway.setDebugEnabled(flag);
    }

}
