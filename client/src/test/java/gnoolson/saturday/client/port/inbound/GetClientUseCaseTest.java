package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.client.ClientHelper;
import gnoolson.saturday.client.GeneralTest;
import gnoolson.saturday.client.application.GetClientUseCaseImpl;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;


class GetClientUseCaseTest extends GeneralTest {

    @Test
    void general() {
        Client client1P1 = ClientHelper.createClient1_p1();

        ClientRepositoryGateway clientRepositoryGateway = Mockito.mock(ClientRepositoryGateway.class);
        Mockito.when(clientRepositoryGateway.find(client1P1.getId())).thenReturn(Optional.of(client1P1));

        GetClientUseCase getClientUseCase = new GetClientUseCaseImpl(clientRepositoryGateway);
        GetClientUseCase.ClientDto result = getClientUseCase.execute(client1P1.getId());

        Assertions.assertEquals(client1P1.getId(), result.getId());
        Assertions.assertEquals(client1P1.getProjectId(), result.getProjectId());
        Assertions.assertEquals(client1P1.getName(), result.getName());
        Assertions.assertEquals(client1P1.getDescription(), result.getDescription());
        Assertions.assertEquals(client1P1.getServerURI(), result.getServerURI());
        Assertions.assertEquals(client1P1.getClientAuth(), result.getClientAuth());
        Assertions.assertEquals(client1P1.getConnectionError(), result.getConnectionError());
        Assertions.assertEquals(client1P1.getConnectedEventHandler(), result.getConnectionEventHandler());
        Assertions.assertEquals(client1P1.getDisconnectedEventHandler(), result.getDisconnectionEventHandler());
    }

}
