package gnoolson.saturday.internal_lua_libs.client;

import gnoolson.saturday.internal_lua_libs.client.lua.ClientInternalService;
import gnoolson.saturday.lua_script_executor.lib.LuaLib;
import gnoolson.saturday.lua_script_executor.lib.LuaLibFactory;

public class ClientLuaLibFactory implements LuaLibFactory {

    @Override
    public String getLuaLibId() {
        return Id.VALUE;
    }

    @Override
    public LuaLib getInstance() {
        return new ClientInternalService();
    }

}
