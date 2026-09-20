package gnoolson.saturday.client.application;

import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.model.vo.OutgoingMessage;
import gnoolson.saturday.client.model.vo.Subscription;
import gnoolson.saturday.client.port.outbound.ScriptLauncherGateway;
import gnoolson.saturday.common.model.vo.ClientId;

import java.util.List;

public interface MQTTClientManager {

    boolean exists(ClientId clientId);

    boolean publish(ClientId clientId, OutgoingMessage outgoingMessage);

    void syncSubscriptions(ClientId clientId, List<Subscription> subscriptions, ScriptLauncherGateway scriptLauncherGateway);

    boolean isConnected(ClientId id);

    void syncConnections(List<Client> clients);

    void disconnect(ClientId clientId);

    void shutdown();

    void checkConnections();

}
