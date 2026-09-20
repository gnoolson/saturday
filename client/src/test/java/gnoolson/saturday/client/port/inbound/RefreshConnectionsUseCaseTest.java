package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.client.ClientHelper;
import gnoolson.saturday.client.GeneralTest;
import gnoolson.saturday.client.application.*;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.model.vo.OutgoingMessage;
import gnoolson.saturday.client.model.vo.Subscription;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.client.port.outbound.CreateMQTTClientGateway;
import gnoolson.saturday.common.model.vo.TopicFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class RefreshConnectionsUseCaseTest extends GeneralTest {

    @Test
    void general() {
        Client client1P1 = ClientHelper.createClient1_p1();
        Client client1P2 = ClientHelper.createClient1_p2();
        Client client1P3 = ClientHelper.createClient1_p3();

        List<MqttClient> mqttClients = new ArrayList<>();

        MQTTClientManager mqttClientManager = new MQTTClientManagerImpl(new CreateMQTTClientGateway() {
            @Override
            public MqttClient execute(Client client) {
                MqttClient mqttClient =  new MqttClient() {
                    boolean connected;

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
                        return "";
                    }
                };

                mqttClients.add(mqttClient);
                return mqttClient;
            }
        }, eventBus);


        ClientRepositoryGateway clientRepositoryGateway = Mockito.mock(ClientRepositoryGateway.class);
        List<Client> list = Arrays.asList(client1P1, client1P2, client1P3);
        Mockito.when(clientRepositoryGateway.findEnabled()).thenReturn(list    );

        RefreshConnectionsUseCase refreshConnectionsUseCase = new RefreshConnectionsUseCaseImpl(clientRepositoryGateway, mqttClientManager);
        refreshConnectionsUseCase.execute();

        Assertions.assertEquals(list.size(), mqttClients.size());

        for (MqttClient mqttClient : mqttClients) {
            Assertions.assertTrue(mqttClient.isConnected());
        }

    }

}