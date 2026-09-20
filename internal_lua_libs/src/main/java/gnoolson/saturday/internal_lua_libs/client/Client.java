package gnoolson.saturday.internal_lua_libs.client;

import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.lua_script_executor.lib.Functionality;

import java.util.Optional;

public interface Client extends Functionality {

    boolean isIncomingMessageAvailable();

    boolean send(OutgoingMessage outgoingMessage);

    Optional<IncomingMessage> getIncomingMessage();

    void setup(ClientId clientId, PublishMessageGateway publishMessageGateway, IncomingMessage incomingMessage);

    void setup(PublishMessageGateway publishMessageGateway);

    void useOutput(boolean flag);

    Optional<ClientId> getClientId();

    void setClientId(ClientId clientId);

}
