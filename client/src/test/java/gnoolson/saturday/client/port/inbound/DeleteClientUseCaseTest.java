package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.client.ClientHelper;
import gnoolson.saturday.client.GeneralTest;
import gnoolson.saturday.client.application.DeleteClientUseCaseImpl;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.common.model.vo.ClientId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;


class DeleteClientUseCaseTest extends GeneralTest {

    @Test
    void general() {
        ClientId clientId = ClientId.of(UUID.randomUUID());

        ClientRepositoryGateway clientRepositoryGateway = Mockito.mock(ClientRepositoryGateway.class);

        Optional<Client> client = ClientHelper.createDisabledClient(clientId);

        Mockito.when(clientRepositoryGateway.find(clientId)).thenReturn(client);

        DeleteClientUseCase deleteClientUseCase = new DeleteClientUseCaseImpl(locker, clientRepositoryGateway, transactionStarter);

        Assertions.assertTrue(deleteClientUseCase.execute(clientId));

        Mockito.verify(clientRepositoryGateway).delete(clientId);
    }

}