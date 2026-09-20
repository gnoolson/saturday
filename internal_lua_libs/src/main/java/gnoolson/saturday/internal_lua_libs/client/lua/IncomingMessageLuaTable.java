package gnoolson.saturday.internal_lua_libs.client.lua;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import gnoolson.saturday.internal_lua_libs.client.IncomingMessage;
import org.luaj.vm2.LuaTable;

public class IncomingMessageLuaTable extends LuaTable {

    public IncomingMessageLuaTable(IncomingMessage incomingMessage) {
        set("topic", incomingMessage.getTopic().getValue());
        set("topicFilter", incomingMessage.getTopicFilter().getValue());
        set("qos", incomingMessage.getQos().getValue());
        set("payload", new ByteArrayLuaTable(incomingMessage.getData().getValue()));
    }

}
