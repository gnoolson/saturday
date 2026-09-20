package gnoolson.saturday.lua_script_executor.lib;

import org.luaj.vm2.lib.VarArgFunction;

public interface LuaFunction extends LuaLib {

    VarArgFunction getInstance();

}
