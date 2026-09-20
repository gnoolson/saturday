package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.client.ClientHelper;
import gnoolson.saturday.client.GeneralTest;
import gnoolson.saturday.client.application.MQTTClientManager;
import gnoolson.saturday.client.application.UpdateClientUseCaseImpl;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;

import gnoolson.saturday.common.model.vo.ClientName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.util.Optional;


class UpdateClientUseCaseTest extends GeneralTest {

    @Test
    void general(){
        ClientName clientName = ClientName.random();
        UpdateClientUseCase.ClientDto client1 = ClientHelper.createClientForUpdateClientUseCase(clientName);

        ClientRepositoryGateway clientRepositoryGateway = Mockito.mock(ClientRepositoryGateway.class);
        Mockito.when(clientRepositoryGateway.find(client1.getId())).thenReturn(Optional.of(ClientHelper.createClient1_p1()));

        MQTTClientManager mqttClientManager = Mockito.mock(MQTTClientManager.class);

        UpdateClientUseCase updateClientUseCase = new UpdateClientUseCaseImpl(locker, clientRepositoryGateway, transactionStarter, mqttClientManager, (projectId, scriptId) -> true);
        updateClientUseCase.execute(client1);

        Mockito.verify(clientRepositoryGateway).save(Mockito.argThat((ArgumentMatcher<Client>) client -> {
            return client.getName().equals(clientName);
        }));
    }

}