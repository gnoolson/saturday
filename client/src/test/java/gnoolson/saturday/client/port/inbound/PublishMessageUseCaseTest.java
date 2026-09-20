package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.client.ClientHelper;
import gnoolson.saturday.client.GeneralTest;
import gnoolson.saturday.client.application.MQTTClientManager;
import gnoolson.saturday.client.application.MQTTClientManagerImpl;
import gnoolson.saturday.client.application.MqttClient;
import gnoolson.saturday.client.application.PublishMessageUseCaseImpl;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.model.vo.OutgoingMessage;
import gnoolson.saturday.client.port.outbound.CreateMQTTClientGateway;
import gnoolson.saturday.common.model.vo.Payload;
import gnoolson.saturday.common.model.vo.QoS;
import gnoolson.saturday.common.model.vo.Topic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

class PublishMessageUseCaseTest extends GeneralTest {

    @Test
    void general() {

        MqttClient mqttClient = Mockito.mock(MqttClient.class);
        Mockito.when(mqttClient.isConnected()).thenReturn(true);
        Mockito.when(mqttClient.publish(new OutgoingMessage(Topic.of("test/topic"), Payload.of("hello".getBytes()), QoS.of(0)))).thenReturn(true);

        MQTTClientManager mqttClientManager = new MQTTClientManagerImpl(new CreateMQTTClientGateway() {
            @Override
            public MqttClient execute(Client client) {
                return mqttClient;
            }
        }, eventBus);

        Client client1P1 = ClientHelper.createClient1_p1();
        mqttClientManager.syncConnections(Arrays.asList(client1P1));

        PublishMessageUseCase publishMessageUseCase = new PublishMessageUseCaseImpl(mqttClientManager);

        boolean result = publishMessageUseCase.execute(client1P1.getId(), Topic.of("test/topic"), Payload.of("hello".getBytes()), QoS.of(0));

        Assertions.assertTrue(result);

    }

}