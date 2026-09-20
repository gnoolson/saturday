package gnoolson.saturday.internal_lua_libs.std;

import gnoolson.saturday.internal_lua_libs.std.lua.StdInternalService;
import gnoolson.saturday.lua_script_executor.lib.LuaLib;
import gnoolson.saturday.lua_script_executor.lib.LuaLibFactory;

public class StdLuaLibFactory implements LuaLibFactory {

    @Override
    public String getLuaLibId() {
        return Id.VALUE;
    }

    @Override
    public LuaLib getInstance() {
        return new StdInternalService();
    }

}
