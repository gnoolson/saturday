package gnoolson.saturday.internal_lua_libs.client_info;

import gnoolson.saturday.internal_lua_libs.client_info.lua.ClientLuaModule;
import gnoolson.saturday.lua_script_executor.lib.LuaLib;
import gnoolson.saturday.lua_script_executor.lib.LuaLibFactory;

public class ClientInfoLuaLibFactory implements LuaLibFactory {

    @Override
    public String getLuaLibId() {
        return Id.VALUE;
    }

    @Override
    public LuaLib getInstance() {
        return new ClientLuaModule();
    }

}
