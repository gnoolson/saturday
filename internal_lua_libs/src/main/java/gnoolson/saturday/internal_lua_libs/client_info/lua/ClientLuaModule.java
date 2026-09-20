package gnoolson.saturday.internal_lua_libs.client_info.lua;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.internal_lua_libs.client_info.Client;
import gnoolson.saturday.internal_lua_libs.client_info.ClientInfo;
import gnoolson.saturday.internal_lua_libs.client_info.Id;
import gnoolson.saturday.lua_script_executor.lib.LuaModule;
import lombok.RequiredArgsConstructor;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import java.util.UUID;

public class ClientLuaModule extends LuaTable implements LuaModule {

    private ClientInfo clientInfo;

    /*
     *
     *
     * */
    public ClientLuaModule() {
        set("byProjectId", new ByProjectIdFunction(this));
        set("byClientId", new ByClientIdFunction(this));
    }

    @Override
    public String getId() {
        return Id.VALUE;
    }

    @Override
    public void updateFunctionality(Object functionality) {
        this.clientInfo = (ClientInfo) functionality;
    }

    @Override
    public void release() {

    }

    @Override
    public String name() {
        return "client_info";
    }

    @Override
    public LuaTable getInstance() {
        return this;
    }

    /*
     *
     *
     * */
    @RequiredArgsConstructor
    public static class ByProjectIdFunction extends OneArgFunction {

        private final ClientLuaModule clientLuaModule;

        @Override
        public LuaValue call(LuaValue projectIdLuaValue) {
            String projectIdStr = LuaArgUtils.getStringFromFunctionArgs(projectIdLuaValue, 1, "projectId");

            LuaTable resultArray = new LuaTable();
            int index = 1;

            for (Client client : clientLuaModule.clientInfo.get(ProjectId.of(UUID.fromString(projectIdStr)))) {
                resultArray.set(index++, new ClientInfoLuaTable(client));
            }

            return resultArray;
        }
    }

    @RequiredArgsConstructor
    public static class ByClientIdFunction extends OneArgFunction {

        private final ClientLuaModule clientLuaModule;

        @Override
        public LuaValue call(LuaValue clientIdLuaValue) {
            String clientIdStr = LuaArgUtils.getStringFromFunctionArgs(clientIdLuaValue, 1, "clientId");
            ClientId clientId = ClientId.of(UUID.fromString(clientIdStr));

            return new ClientInfoLuaTable(clientLuaModule.clientInfo.get(clientId));
        }
    }

}
