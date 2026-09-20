package gnoolson.saturday.app.script;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.internal_lua_libs.log.LogGateway;
import gnoolson.saturday.script.port.outbound.ClearLogFileGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClearLogFileGatewayImpl implements ClearLogFileGateway {

    private final gnoolson.saturday.common.cache.Cache<ScriptId, LogGateway> logCache;
    private final Locker locker;

    /*
     *
     *
     * */
    @Override
    public boolean execute(ScriptId id) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(id))) {
            LogGateway logGateway = logCache.getAndProlongOrCreate(id);
            return logGateway.clear();
        }
    }

}
