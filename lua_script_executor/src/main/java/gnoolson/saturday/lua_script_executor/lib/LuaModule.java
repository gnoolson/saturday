package gnoolson.saturday.lua_script_executor.lib;

import org.luaj.vm2.LuaTable;

public interface LuaModule extends LuaLib {

    LuaTable getInstance();

}
