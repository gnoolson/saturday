package gnoolson.saturday.internal_lua_libs.client_info.lua;

import gnoolson.saturday.internal_lua_libs.client_info.Client;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class ClientInfoLuaTable extends LuaTable {

    public ClientInfoLuaTable(Client client) {
        set("name", LuaValue.valueOf(client.getName().getValue()));
        set("id", LuaValue.valueOf(client.getId().getValue().toString()));
        set("uri", LuaValue.valueOf(client.getURI().getValue()));
        set("connected", LuaValue.valueOf(client.isConnected()));
        set("projectId", LuaValue.valueOf(client.getProjectId().getValue().toString()));
    }

}
