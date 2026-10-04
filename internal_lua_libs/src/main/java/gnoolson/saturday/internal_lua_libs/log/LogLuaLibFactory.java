package gnoolson.saturday.internal_lua_libs.log;

import gnoolson.saturday.internal_lua_libs.log.lua.LogInternalService;
import gnoolson.saturday.lua_script_executor.lib.LuaLib;
import gnoolson.saturday.lua_script_executor.lib.LuaLibFactory;

public class LogLuaLibFactory implements LuaLibFactory {

    @Override
    public String getLuaLibId() {
        return Id.VALUE;
    }

    @Override
    public LuaLib getInstance() {
        return new LogInternalService();
    }

}
