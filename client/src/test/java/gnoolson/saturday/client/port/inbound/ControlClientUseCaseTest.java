package gnoolson.saturday.client.port.inbound;

import gnoolson.locker.Locker;
import gnoolson.locker.OptimisticLocalLocker;
import gnoolson.saturday.client.ClientHelper;
import gnoolson.saturday.client.GeneralTest;
import gnoolson.saturday.client.application.ControlClientUseCaseImpl;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.common.model.vo.ClientId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

class ControlClientUseCaseTest extends GeneralTest {

    @Test
    void general() {
        Locker locker = new OptimisticLocalLocker();
        ClientRepositoryGateway clientRepositoryGateway = Mockito.mock(ClientRepositoryGateway.class);

        ClientId clientId = ClientId.of(UUID.randomUUID());

        Mockito.when(clientRepositoryGateway.find(clientId)).thenReturn(ClientHelper.createDisabledClient(clientId));

        ControlClientUseCase controlClientUseCase = new ControlClientUseCaseImpl(locker, clientRepositoryGateway, transactionStarter);

        controlClientUseCase.execute(clientId, true);

        Mockito.verify(clientRepositoryGateway).save(Mockito.argThat(client -> client.getId().equals(clientId) && client.isEnabled()));
    }

}