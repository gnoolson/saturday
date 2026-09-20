package gnoolson.saturday.client.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.model.vo.Subscription;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.client.port.outbound.ScriptLauncherGateway;
import gnoolson.saturday.client.port.outbound.SubscriptionRepositoryGateway;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ClientId;
import lombok.RequiredArgsConstructor;

import java.util.List;

// TODO
@RequiredArgsConstructor
public class ClientSubscriptionUpdater {

    private final MQTTClientManager mqttClientManager;
    private final ScriptLauncherGateway scriptLauncherGateway;
    private final SubscriptionRepositoryGateway subscriptionRepositoryGateway;
    private final Locker locker;
    private final ClientRepositoryGateway clientRepositoryGateway;

    /*
     *
     *
     * */
    public void updateSubscriptionsForClient(ClientId clientId) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(clientId))) {
            List<Subscription> subscriptionList = subscriptionRepositoryGateway.findByClientId(clientId);
            mqttClientManager.syncSubscriptions(clientId, subscriptionList, scriptLauncherGateway);
        }
    }

    public void updateAll() {
        List<Client> clients = clientRepositoryGateway.findAll();
        for (Client client : clients) {
            updateSubscriptionsForClient(client.getId());
        }
    }

}
