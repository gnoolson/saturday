package gnoolson.saturday.app.script;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.cache.Cache;
import gnoolson.saturday.common.eventbus.Callback;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ScriptUpdatedEvent;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.lua_script_executor.Sandbox;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;

import java.util.List;

public class ScriptUpdatedEventListener implements Callback<ScriptUpdatedEvent> {

    private final Cache<ScriptId, Sandbox> sandboxCache;
    private final ScriptRepositoryGateway scriptRepositoryGateway;
    private final Locker locker;

    /*
     *
     *
     * */
    public ScriptUpdatedEventListener(EventBus eventBus, Cache<ScriptId, Sandbox> sandboxCache, ScriptRepositoryGateway scriptRepositoryGateway, Locker locker) {
        this.sandboxCache = sandboxCache;
        this.scriptRepositoryGateway = scriptRepositoryGateway;
        this.locker = locker;
        eventBus.on(ScriptUpdatedEvent.class, this);
    }

    /*
     *
     *
     * */
    private static String[] createKeysForLock(List<ScriptId> owners) {
        return owners.stream().map((scriptId) -> scriptId.getValue().toString()).toArray(String[]::new);
    }

    @Override
    public void exec(ScriptUpdatedEvent event) {

        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(event.getScriptId()))) {
            List<ScriptId> scripts = scriptRepositoryGateway.findOwners(event.getScriptId());
            String[] keys = createKeysForLock(scripts);

            try (Locker.LockHandle ignore2 = locker.lockIds(keys)) {
                sandboxCache.remove(event.getScriptId());

                for (ScriptId scriptId : scripts) {
                    sandboxCache.remove(scriptId);
                }
            }
        }

    }


}
