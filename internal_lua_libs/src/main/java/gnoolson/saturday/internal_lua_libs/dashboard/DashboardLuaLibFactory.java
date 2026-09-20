package gnoolson.saturday.internal_lua_libs.dashboard;

import gnoolson.saturday.internal_lua_libs.dashboard.lua.DashboardInternalService;
import gnoolson.saturday.lua_script_executor.lib.LuaLib;
import gnoolson.saturday.lua_script_executor.lib.LuaLibFactory;

public class DashboardLuaLibFactory implements LuaLibFactory {

    @Override
    public String getLuaLibId() {
        return Id.VALUE;
    }

    @Override
    public LuaLib getInstance() {
        return new DashboardInternalService();
    }


}
