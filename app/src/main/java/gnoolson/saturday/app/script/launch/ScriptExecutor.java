package gnoolson.saturday.app.script.launch;

import gnoolson.saturday.app.script.ScriptData;
import gnoolson.saturday.app.script.ScriptDataProvider;
import gnoolson.saturday.app.script.lib.LuaLibProvider;
import gnoolson.saturday.common.cache.Cache;
import gnoolson.saturday.common.cache.CachingTime;
import gnoolson.saturday.common.cache.Entity;
import gnoolson.saturday.common.cache.EntityProvider;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.lua_script_executor.LuaScriptExecutor;
import gnoolson.saturday.lua_script_executor.Sandbox;
import gnoolson.saturday.lua_script_executor.Script;
import gnoolson.saturday.lua_script_executor.lib.Functionality;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ScriptExecutor {

    private final File luaLib;
    private final boolean luajFullAccess;
    private final LuaScriptExecutor luaScriptExecutor;
    private final Cache<ScriptId, Sandbox> cache;
    private final LuaLibProvider luaLibProvider;

    /*
     *
     *
     * */
    public long execute(Collection<Functionality> functionalities, ScriptId scriptId, ScriptDataProvider scriptDataProvider) {
        Sandbox sandbox = cache.getAndProlongOrCreate(scriptId, new EntityProviderImpl(scriptId, scriptDataProvider));

        sandbox.setupFunctionalities(functionalities);
        return luaScriptExecutor.execute(sandbox);
    }

    /*
     *
     *
     * */
    @RequiredArgsConstructor
    public class EntityProviderImpl implements EntityProvider<ScriptId, Sandbox> {

        private final ScriptId scriptId;
        private final ScriptDataProvider scriptDataProvider;

        @Override
        public Entity<Sandbox> create(ScriptId id) {
            ScriptData scriptData = scriptDataProvider.execute(scriptId);

            Sandbox cachedSandbox = new Sandbox(
                    new gnoolson.saturday.lua_script_executor.ScriptData(
                            new Script(
                                    scriptData.getScript().getName().getValue(),
                                    scriptData.getScript().getCode().getValue()
                            ),
                            scriptData.getIncludedScripts().stream().map(obj -> {
                                return new Script(
                                        obj.getName().getValue(),
                                        obj.getCode().getValue()
                                );
                            }).collect(Collectors.toList())
                    ),
                    luajFullAccess,
                    luaLibProvider.getLibs(),
                    luaLib
            );

            return new Entity<Sandbox>() {
                @Override
                public CachingTime getCachingTime() {
                    return scriptData.getCachingTime();
                }

                @Override
                public Sandbox getValue() {
                    return cachedSandbox;
                }

                @Override
                public void destroy() {
                }
            };
        }

    }

}
