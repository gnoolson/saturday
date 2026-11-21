package gnoolson.saturday.client.application;

import gnoolson.saturday.client.model.entity.Client;
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
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Log4j2
public class MQTTClientManagerImpl implements MQTTClientManager {

    private final Map<ClientId, ManagedClient> managedClients = new ConcurrentHashMap<>();
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
        return managedClients.containsKey(clientId);
    }

    @Override
    public boolean publish(ClientId clientId, OutgoingMessage outgoingMessage) {
        ManagedClient client = managedClients.get(clientId);

        if (client != null) {
            client.getMqttClient().publish(outgoingMessage);
            return true;
        } else {
            log.warn("Client \"{}\" was not found", clientId.getValue());
            return false;
        }
    }

    @Override
    public void syncSubscriptions(ClientId clientId, List<Subscription> subscriptions, ScriptLauncherGateway scriptLauncherGateway) {
        ManagedClient client = managedClients.get(clientId);
        if (client == null) {
            return;
        }

        client.getMqttClient().syncSubscriptions(subscriptions,
                (subscriptionId, scriptId, message) -> scriptLauncherGateway.execute(scriptId, client.getClientId(), subscriptionId, message)
        );
    }

    @Override
    public boolean isConnected(ClientId id) {
        ManagedClient client = managedClients.get(id);
        return client != null && client.getMqttClient().isConnected();
    }

    @Override
    public void syncClients(List<Client> enabledClients) {
        disconnectRemovedClients(enabledClients);
        registerNewClients(enabledClients);
    }

    @Override
    public void disconnect(ClientId clientId) {
        ManagedClient removedClient = managedClients.remove(clientId);
        if (removedClient != null) {
            removedClient.getMqttClient().disconnectAndClose();
            if (removedClient.isWasConnected()) {
                eventBus.emit(new ClientDisconnectedEvent(clientId));
            }
        } else {
            log.warn("Client \"{}\" was not found for disconnect", clientId.getValue());
        }
    }

    @Override
    public void shutdown() {
        for (ClientId clientId : managedClients.keySet()) {
            disconnect(clientId);
        }
    }

    @Override
    public void checkAndReconnectConnections() {
        for (ManagedClient client : managedClients.values()) {
            checkAndReconnect(client);
        }
    }

    /*
     *
     *
     * */
    private void registerNewClients(List<Client> enabledClients) {
        for (Client enabledClient : enabledClients) {
            managedClients.computeIfAbsent(enabledClient.getId(), id -> {
                MqttClient mqttClient = createMqttClientGateway.execute(enabledClient);
                ManagedClient managedClient = new ManagedClient(
                        enabledClient.getProjectId(),
                        enabledClient.getId(),
                        enabledClient.getName(),
                        mqttClient
                );
                return managedClient;
            });
        }
    }

    private void disconnectRemovedClients(List<Client> enabledClients) {
        Set<ClientId> enabledIds = new HashSet<>(enabledClients.size());
        for (Client enabledClient : enabledClients) {
            enabledIds.add(enabledClient.getId());
        }

        for (ClientId currentId : managedClients.keySet()) {
            if (!enabledIds.contains(currentId)) {
                disconnect(currentId);
            }
        }
    }

    private void checkAndReconnect(ManagedClient client) {
        MqttClient mqttClient = client.getMqttClient();

        if (mqttClient.isConnected()) {
            if (!client.isWasConnected()) {
                client.setWasConnected(true);
                eventBus.emit(new ClientConnectedEvent(client.getProjectId(), client.getClientId()));
            }
            return;
        }

        if (client.isWasConnected()) {
            client.setWasConnected(false);
            eventBus.emit(new ClientDisconnectedEvent(client.getClientId()));
        }

        tryConnect(client);
    }

    private void tryConnect(ManagedClient client) {
        try {
            client.getMqttClient().connect();
            client.setWasConnected(true);
            eventBus.emit(new ClientConnectedEvent(client.getProjectId(), client.getClientId()));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("Failed to connect client \"{}\": {}", client.getClientId().getValue(), e.getMessage());
            }
            eventBus.emit(new ClientConnectionExceptionEvent(client.getClientId(), e));
        }
    }

    /*
     *
     *
     * */
    @Getter
    @RequiredArgsConstructor
    private static class ManagedClient {
        private final ProjectId projectId;
        private final ClientId clientId;
        private final ClientName clientName;
        private final MqttClient mqttClient;

        @Setter
        private volatile boolean wasConnected = false;
    }
}
