package gnoolson.saturday.internal_lua_libs.client.lua;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.QoS;
import gnoolson.saturday.common.model.vo.Topic;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import gnoolson.saturday.internal_lua_libs.client.Client;
import gnoolson.saturday.internal_lua_libs.client.Id;
import gnoolson.saturday.internal_lua_libs.client.IncomingMessage;
import gnoolson.saturday.internal_lua_libs.client.OutgoingMessage;
import gnoolson.saturday.lua_script_executor.lib.InternalServiceLuaTable;
import lombok.RequiredArgsConstructor;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import java.util.Optional;
import java.util.UUID;

public class ClientInternalService extends LuaTable implements InternalServiceLuaTable {

    private Client client;

    /*
     *
     *
     * */
    public ClientInternalService() {
        set("isIncomingMessageAvailable", new IsIncomingMessageAvailableFunction(this));
        set("setup", new SetupFunction(this));
        set("getIncomingMessage", new GetIncomingMessageFunction(this));
        set("sendMessage", new SendMessageFunction(this));
    }

    @Override
    public String name() {
        return "Client";
    }

    @Override
    public String getId() {
        return Id.VALUE;
    }

    @Override
    public void updateFunctionality(Object functionality) {
        this.client = (Client) functionality;

        if (client.getClientId().isPresent()) {
            ClientId clientId = client.getClientId().get();
            set("clientId", clientId.getValue().toString());
        }
    }

    @Override
    public void release() {

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
    public static class SendMessageFunction extends OneArgFunction {

        private final ClientInternalService clientInternalService;

        @Override
        public LuaValue call(LuaValue messageLuaTable) {
            LuaTable message = LuaArgUtils.getLuaTableFromFunctionArgs(messageLuaTable, 1, "message");

            String topic = LuaArgUtils.getStringFromLuaTable(message, "message", "topic");
            int qos = LuaArgUtils.getIntFromLuaTable(message, "message", "qos");
            ByteArrayLuaTable payloadLuaTable = (ByteArrayLuaTable) LuaArgUtils.getLuaTableFromLuaTable(message, "message", "payload");
            byte[] payload = payloadLuaTable.getBytes();

            boolean result = clientInternalService.client.send(new OutgoingMessage(Topic.of(topic), QoS.of(qos), payload));

            return LuaBoolean.valueOf(result);
        }


    }

    @RequiredArgsConstructor
    public static class IsIncomingMessageAvailableFunction extends ZeroArgFunction {

        private final ClientInternalService clientInternalService;

        @Override
        public LuaValue call() {
            return clientInternalService.client.isIncomingMessageAvailable() ? LuaBoolean.TRUE : LuaBoolean.FALSE;
        }
    }

    @RequiredArgsConstructor
    public static class GetIncomingMessageFunction extends ZeroArgFunction {

        private final ClientInternalService clientInternalService;

        @Override
        public LuaValue call() {
            Optional<IncomingMessage> messageOpt = clientInternalService.client.getIncomingMessage();
            if (!messageOpt.isPresent())
                return LuaValue.NIL;

            IncomingMessage incomingMessage = messageOpt.get();
            return new IncomingMessageLuaTable(incomingMessage);
        }
    }

    @RequiredArgsConstructor
    public static class SetupFunction extends OneArgFunction {

        private final ClientInternalService clientInternalService;

        @Override
        public LuaValue call(LuaValue clientIdLuaValue) {
            String clientIdStr = LuaArgUtils.getStringFromFunctionArgs(clientIdLuaValue, 1, "clientId");

            ClientId clientId = ClientId.of(UUID.fromString(clientIdStr));
            clientInternalService.set("clientId", clientIdLuaValue);
            clientInternalService.client.setClientId(clientId);

            return LuaValue.NIL;
        }

    }

}
