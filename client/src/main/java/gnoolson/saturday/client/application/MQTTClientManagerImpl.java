package gnoolson.saturday.client.application;

import gnoolson.saturday.client.model.vo.OutgoingMessage;
import gnoolson.saturday.client.model.vo.Subscription;
import gnoolson.saturday.client.port.outbound.CreateMQTTClientGateway;
import gnoolson.saturday.client.port.outbound.ScriptLauncherGateway;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ClientConnectedEvent;
import gnoolson.saturday.common.eventbus.events.ClientConnectionExceptionEvent;
import gnoolson.saturday.common.eventbus.events.ClientDisconnectedEvent;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ClientName;
import gnoolson.saturday.common.model.vo.ProjectId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Log4j2
public class MQTTClientManagerImpl implements MQTTClientManager {

    private final List<Client> connectedClients = new CopyOnWriteArrayList<>();
    private final CreateMQTTClientGateway createMqttClientGateway;
    private final EventBus eventBus;

    /*
     *
     *
     * */
    public MQTTClientManagerImpl(CreateMQTTClientGateway createMqttClientGateway, EventBus eventBus) {
        this.createMqttClientGateway = createMqttClientGateway;
        this.eventBus = eventBus;
    }

    @Override
    public boolean exists(ClientId clientId) {
        return getClient(clientId).isPresent();
    }

    @Override
    public boolean publish(ClientId clientId, OutgoingMessage outgoingMessage) {
        Optional<Client> clientOpt = getClient(clientId);

        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            return client.getMqttClient().publish(outgoingMessage);
        } else {
            log.warn("Client \"{}\" was not found", clientId.getValue().toString());
            return false;
        }
    }

    @Override
    public void syncSubscriptions(ClientId clientId, List<Subscription> subscriptions, ScriptLauncherGateway scriptLauncherGateway) {
        Optional<Client> clientOpt = getClient(clientId);
        if (!clientOpt.isPresent())
            return;

        Client client = clientOpt.get();
        client.getMqttClient().syncSubscriptions(subscriptions,
                (subscriptionId, scriptId, message) -> scriptLauncherGateway.execute(scriptId, client.getClientId(), subscriptionId, message)
        );
    }

    @Override
    public boolean isConnected(ClientId id) {
        Optional<Client> clientOpt = getClient(id);
        if (!clientOpt.isPresent())
            return false;

        return clientOpt.get().getMqttClient().isConnected();
    }

    @Override
    public void syncConnections(List<gnoolson.saturday.client.model.entity.Client> clients) {
        disconnectionIfNeed(clients);
        connectionIfNeed(clients);
    }

    @Override
    public void disconnect(ClientId clientId) {
        disconnectAndClose(clientId);
    }

    @Override
    public void shutdown() {
        for (Client connectedClient : connectedClients) {
            disconnect(connectedClient.getClientId());
        }
    }

    @Override
    public void checkConnections() {
        for (Client connectedClient : connectedClients) {
            checkConnection(connectedClient.getClientId());
        }
    }

    /*
     *
     *
     * */
    private Optional<Client> getClient(ClientId clientId) {
        for (Client client : connectedClients) {
            if (client.getClientId().equals(clientId))
                return Optional.of(client);
        }

        return Optional.empty();
    }

    private boolean connect(gnoolson.saturday.client.model.entity.Client client) {
        if (exists(client.getId()))
            throw new RuntimeException(String.format("Client \"%s\" is already managed by MQTTClientManager", client.getId().toString())); // +

        MqttClient mqttClient = createMqttClientGateway.execute(client);
        try {
            mqttClient.connect();
            connectedClients.add(new Client(client.getProjectId(), client.getId(), client.getName(), mqttClient));
            eventBus.emit(new ClientConnectedEvent(client.getProjectId(), client.getId()));
            return true;
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception", e);
            eventBus.emit(new ClientConnectionExceptionEvent(client.getId(), e));
        }
        return false;
    }

    private void disconnectAndClose(ClientId clientId) {
        try {
            Optional<Client> client = getClient(clientId);
            if (!client.isPresent())
                throw new RuntimeException(String.format("Client \"%s\" was not found", clientId.toString())); // +

            MqttClient mqttClient = client.get().getMqttClient();
            mqttClient.disconnectAndClose();
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception", e);
        }
    }

    private void disconnectionIfNeed(List<gnoolson.saturday.client.model.entity.Client> enabledClients) {
        for (Client client : connectedClients) {
            boolean notFound = true;
            for (gnoolson.saturday.client.model.entity.Client enabledClient : enabledClients) {
                if (enabledClient.getId().equals(client.getClientId())) {
                    notFound = false;
                    break;
                }
            }

            if (notFound) {
                disconnectAndClose(client.getClientId());
            }
        }
    }

    private void connectionIfNeed(List<gnoolson.saturday.client.model.entity.Client> enabledClients) {
        for (gnoolson.saturday.client.model.entity.Client enabledClient : enabledClients) {
            if (!exists(enabledClient.getId())) {
                connect(enabledClient);
            }
        }
    }

    private void checkConnection(ClientId clientId) {
        Optional<Client> client = getClient(clientId);
        if (!client.isPresent()) {
            log.warn("Client \"{}\" was not found", clientId.getValue().toString());
            return;
        }

        MqttClient mqttClient = client.get().getMqttClient();
        if (mqttClient.isConnected())
            return;

        disconnectAndClose(clientId);
        remove(clientId);
        eventBus.emit(new ClientDisconnectedEvent(clientId));
    }

    private void remove(ClientId clientId) {
        connectedClients.removeIf(client -> client.getClientId().equals(clientId));
    }

    /*
     *
     *
     * */

    @Getter
    @RequiredArgsConstructor
    private class Client {
        private final ProjectId projectId;
        private final ClientId clientId;
        private final ClientName clientName;
        private final MqttClient mqttClient;
    }

}
