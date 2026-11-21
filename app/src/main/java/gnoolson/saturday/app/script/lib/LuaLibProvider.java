package gnoolson.saturday.app.script.lib;

import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.lua_script_executor.lib.LuaLib;
import gnoolson.saturday.lua_script_executor.lib.LuaLibFactory;

import java.util.List;

public interface LuaLibProvider {

    List<LuaLib> initLibs(ScriptId scriptId);

    void addFactory(LuaLibFactory luaLibFactory);

    void remove(String pluginId);

    void addFactory(String pluginId, gnoolson.saturday_plugin_api.LuaModuleFactory luaLibFactory);

}
