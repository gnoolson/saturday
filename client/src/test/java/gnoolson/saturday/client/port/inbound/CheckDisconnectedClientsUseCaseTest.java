package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.client.ClientHelper;
import gnoolson.saturday.client.GeneralTest;
import gnoolson.saturday.client.application.*;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.model.vo.OutgoingMessage;
import gnoolson.saturday.client.model.vo.Subscription;
import gnoolson.saturday.client.port.outbound.CreateMQTTClientGateway;
import gnoolson.saturday.common.eventbus.Callback;
import gnoolson.saturday.common.eventbus.events.ClientDisconnectedEvent;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.TopicFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CheckDisconnectedClientsUseCaseTest extends GeneralTest {

    @Test
    void general() {
        List<MqttClient> mqttClients = new ArrayList<>();

        CreateMQTTClientGateway createMQTTClientGateway = new CreateMQTTClientGateway() {
            @Override
            public MqttClient execute(Client client) {
                MqttClient mqttClient = new MqttClient() {

                    private boolean connected;

                    @Override
                    public void connect() throws Exception {
                        connected = true;
                    }

                    @Override
                    public boolean isConnected() {
                        return connected;
                    }

                    @Override
                    public void disconnectAndClose() {
                        connected = false;
                    }

                    @Override
                    public void syncSubscriptions(List<Subscription> subscriptions, MessageArrivedCallback messageArrivedCallback) {

                    }

                    @Override
                    public boolean publish(OutgoingMessage outgoingMessage) {
                        return false;
                    }

                    @Override
                    public boolean isSubscribed(TopicFilter topicFilter) {
                        return false;
                    }

                    @Override
                    public String getName() {
                        return client.getName().getValue();
                    }
                };
                mqttClients.add(mqttClient);
                return mqttClient;
            }
        };

        List<ClientId> disconnectedClients = new ArrayList<>();
        eventBus.on(ClientDisconnectedEvent.class, new Callback<ClientDisconnectedEvent>() {
            @Override
            public void exec(ClientDisconnectedEvent event) {
                disconnectedClients.add(event.getClientId());
            }
        });

        List<Client> list = Arrays.asList(
                ClientHelper.createClient1_p1(),
                ClientHelper.createClient2_p1(),
                ClientHelper.createClient3_p1()
        );

        MQTTClientManager mqttClientManager = new MQTTClientManagerImpl(createMQTTClientGateway, eventBus);
        mqttClientManager.syncConnections(list);

        for (Client client : list) {
            Assertions.assertTrue(mqttClientManager.isConnected(client.getId()));
        }

        CheckDisconnectedClientsUseCase checkDisconnectedClientsUseCase = new CheckDisconnectedClientsUseCaseImpl(mqttClientManager);
        checkDisconnectedClientsUseCase.execute();

        for (Client client : list) {
            Assertions.assertTrue(mqttClientManager.isConnected(client.getId()));
        }

        for (MqttClient mqttClient : mqttClients) {
            mqttClient.disconnectAndClose();
        }

        for (Client client : list) {
            Assertions.assertFalse(mqttClientManager.isConnected(client.getId()));
        }

        checkDisconnectedClientsUseCase.execute();

        int counter = 0;
        for (ClientId disconnectedClient : disconnectedClients) {
            for (Client client : list) {
                if (client.getId().equals(disconnectedClient))
                    counter++;
            }
        }

        Assertions.assertEquals(list.size(), counter);
    }

}