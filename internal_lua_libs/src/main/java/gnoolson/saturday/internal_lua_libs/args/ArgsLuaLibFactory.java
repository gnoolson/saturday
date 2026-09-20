package gnoolson.saturday.internal_lua_libs.args;

import gnoolson.saturday.internal_lua_libs.args.lua.ArgsInternalService;
import gnoolson.saturday.lua_script_executor.lib.LuaLib;
import gnoolson.saturday.lua_script_executor.lib.LuaLibFactory;

public class ArgsLuaLibFactory implements LuaLibFactory {

    @Override
    public String getLuaLibId() {
        return Id.VALUE;
    }

    @Override
    public LuaLib getInstance() {
        return new ArgsInternalService();
    }


}
