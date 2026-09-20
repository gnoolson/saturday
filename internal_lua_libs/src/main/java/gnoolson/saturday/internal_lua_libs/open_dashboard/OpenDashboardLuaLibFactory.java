package gnoolson.saturday.internal_lua_libs.open_dashboard;

import gnoolson.saturday.internal_lua_libs.open_dashboard.lua.OpenDashboardLuaModule;
import gnoolson.saturday.lua_script_executor.lib.LuaLib;
import gnoolson.saturday.lua_script_executor.lib.LuaLibFactory;

public class OpenDashboardLuaLibFactory implements LuaLibFactory {

    @Override
    public String getLuaLibId() {
        return Id.VALUE;
    }

    @Override
    public LuaLib getInstance() {
        return new OpenDashboardLuaModule();
    }

}
