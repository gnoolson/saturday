package gnoolson.saturday.app.script;

import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.internal_lua_libs.log.LogGateway;
import gnoolson.saturday.script.port.outbound.IsDebugEnabledGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsDebugEnabledGatewayImpl implements IsDebugEnabledGateway {

    private final gnoolson.saturday.common.cache.Cache<ScriptId, LogGateway> logCache;

    /*
     *
     *
     * */
    @Override
    public boolean execute(ScriptId id) {
        return logCache.getAndProlongOrCreate(id).isDebugEnabled();
    }

}
