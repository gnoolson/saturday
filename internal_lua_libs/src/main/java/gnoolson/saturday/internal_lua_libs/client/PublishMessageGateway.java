package gnoolson.saturday.internal_lua_libs.client;

import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.script.model.vo.Message;

public interface PublishMessageGateway {

    boolean execute(ClientId clientId, Message message);

}
