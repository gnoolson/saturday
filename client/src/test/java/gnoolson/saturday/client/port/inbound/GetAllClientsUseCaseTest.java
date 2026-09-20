package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.client.ClientHelper;
import gnoolson.saturday.client.GeneralTest;
import gnoolson.saturday.client.application.*;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.client.port.outbound.CreateMQTTClientGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

class GetAllClientsUseCaseTest extends GeneralTest {

    @Test
    void general(){

        Client client1 = ClientHelper.createClient1_p1();
        Client client2 = ClientHelper.createClient2_p1();
        Client client3 = ClientHelper.createClient3_p1();
        Client client4 = ClientHelper.createClient4_p1();

        ClientRepositoryGateway clientRepositoryGateway = Mockito.mock(ClientRepositoryGateway.class);
        Mockito.when(clientRepositoryGateway.findAll()).thenReturn(Arrays.asList(
                client1, client2, client3, client4
        ));

        CreateMQTTClientGateway createMQTTClientGateway = Mockito.mock(CreateMQTTClientGateway.class);
        MQTTClientManager mqttClientManager = new MQTTClientManagerImpl(createMQTTClientGateway, eventBus);

        {
            Mockito.when(createMQTTClientGateway.execute(client2)).thenReturn(MqttClientHelper.createConnected());
            Mockito.when(createMQTTClientGateway.execute(client3)).thenReturn(MqttClientHelper.createConnected());
        }

        {
            mqttClientManager.syncConnections(Arrays.asList(client2, client3));
            client3.disable();
            client4.disable();
        }

        GetAllClientsUseCase getAllClientsUseCase = new GetAllClientsUseCaseImpl(clientRepositoryGateway, mqttClientManager);

        {
            List<GetAllClientsUseCase.ClientDto> result = getAllClientsUseCase.execute();

            GetAllClientsUseCase.ClientDto clientDto1 = result.stream().filter((dto) -> dto.getId().equals(client1.getId())).findFirst().get();
            Assertions.assertEquals(GetAllClientsUseCase.ConnectionStatus.CONNECTION, clientDto1.getConnectionStatus());

            GetAllClientsUseCase.ClientDto clientDto2 = result.stream().filter((dto) -> dto.getId().equals(client2.getId())).findFirst().get();
            Assertions.assertEquals(GetAllClientsUseCase.ConnectionStatus.CONNECTED, clientDto2.getConnectionStatus());

            GetAllClientsUseCase.ClientDto clientDto3 = result.stream().filter((dto) -> dto.getId().equals(client3.getId())).findFirst().get();
            Assertions.assertEquals(GetAllClientsUseCase.ConnectionStatus.DISCONNECTION, clientDto3.getConnectionStatus());

            GetAllClientsUseCase.ClientDto clientDto4 = result.stream().filter((dto) -> dto.getId().equals(client4.getId())).findFirst().get();
            Assertions.assertEquals(GetAllClientsUseCase.ConnectionStatus.DISCONNECTED, clientDto4.getConnectionStatus());
        }

    }

}