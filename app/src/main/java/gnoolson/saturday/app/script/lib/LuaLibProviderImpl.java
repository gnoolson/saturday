package gnoolson.saturday.app.script.lib;

import gnoolson.saturday.lua_script_executor.lib.LuaLib;
import gnoolson.saturday.lua_script_executor.lib.LuaLibFactory;
import gnoolson.saturday.lua_script_executor.lib.LuaModule;
import gnoolson.saturday_plugin_api.Functionality;
import org.luaj.vm2.LuaTable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LuaLibProviderImpl implements LuaLibProvider {

    private final List<LuaLibFactory> luaLibFactories = new CopyOnWriteArrayList<>();

    /*
     *
     *
     * */
    @Override
    public List<LuaLib> getLibs() {
        List<LuaLib> result = new ArrayList<>();

        for (LuaLibFactory luaLibFactory : luaLibFactories) {
            result.add(luaLibFactory.getInstance());
        }

        return result;
    }

    @Override
    public void addFactory(LuaLibFactory newLuaLibFactory) {
        for (LuaLibFactory luaLibFactory : luaLibFactories) {
            if (luaLibFactory.getLuaLibId().equals(newLuaLibFactory.getLuaLibId()))
                throw new RuntimeException(String.format("Duplicate LuaLibFactory was found: \"%s\"", newLuaLibFactory.getLuaLibId())); //+
        }

        luaLibFactories.add(newLuaLibFactory);
    }

    @Override
    public void remove(String pluginId) {
        luaLibFactories.removeIf(luaLibFactory -> {
            return luaLibFactory.getLuaLibId().equals(Prefix.VALUE + pluginId);
        });
    }

    @Override
    public void addFactory(String pluginId, gnoolson.saturday_plugin_api.LuaModuleFactory luaModuleFactory) {

        addFactory(new LuaLibFactory() {
            @Override
            public String getLuaLibId() {
                return Prefix.VALUE + pluginId;
            }

            @Override
            public LuaLib getInstance() {
                gnoolson.saturday_plugin_api.LuaModule module = luaModuleFactory.getInstance();
                return new LuaModule() {
                    @Override
                    public String name() {
                        return module.name();
                    }

                    @Override
                    public LuaTable getInstance() {
                        return module;
                    }

                    @Override
                    public String getId() {
                        return Prefix.VALUE + pluginId;
                    }

                    @Override
                    public void updateFunctionality(Object functionality) {
                        module.updateFunctionality((Functionality) functionality);
                    }

                    @Override
                    public void release() {
                        module.release();
                    }
                };
            }
        });
    }

}
