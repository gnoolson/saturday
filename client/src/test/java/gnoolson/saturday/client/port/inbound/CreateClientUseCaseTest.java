package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.client.ClientHelper;
import gnoolson.saturday.client.GeneralTest;
import gnoolson.saturday.client.application.CreateClientUseCaseImpl;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.common.model.vo.ClientId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

class CreateClientUseCaseTest extends GeneralTest {

    @Test
    void general() {
        ClientRepositoryGateway clientRepositoryGateway = Mockito.mock(ClientRepositoryGateway.class);
        ClientId clientId = ClientId.of(UUID.randomUUID());

        CreateClientUseCase.ClientDto clientForCreateClientUseCase = ClientHelper.createClientForCreateClientUseCase();

        Mockito.when(clientRepositoryGateway.save(Mockito.argThat(client -> {
            return client.getName().equals(clientForCreateClientUseCase.getName());
        }))).thenReturn(clientId);

        CreateClientUseCase createClientUseCase = new CreateClientUseCaseImpl(clientRepositoryGateway, locker,
                transactionStarter, (projectId, scriptId) -> false, id -> true);

        ClientId result = createClientUseCase.execute(clientForCreateClientUseCase);

        Assertions.assertEquals(clientId, result);
    }

}