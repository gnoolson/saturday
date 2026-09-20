package gnoolson.saturday.internal_lua_libs.output;

import gnoolson.saturday.internal_lua_libs.output.lua.OutputInternalService;
import gnoolson.saturday.lua_script_executor.lib.LuaLib;
import gnoolson.saturday.lua_script_executor.lib.LuaLibFactory;

public class OutputLuaLibFactory implements LuaLibFactory {

    @Override
    public String getLuaLibId() {
        return Id.VALUE;
    }

    @Override
    public LuaLib getInstance() {
        return new OutputInternalService();
    }

}
