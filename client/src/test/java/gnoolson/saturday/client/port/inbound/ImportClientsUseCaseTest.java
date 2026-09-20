package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.client.ClientHelper;
import gnoolson.saturday.client.application.ImportClientsUseCaseImpl;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

class ImportClientsUseCaseTest {

    @Test
    void general() {
        ClientRepositoryGateway clientRepositoryGateway = Mockito.mock(ClientRepositoryGateway.class);

        ImportClientsUseCase importClientsUseCase = new ImportClientsUseCaseImpl(clientRepositoryGateway, (projectId, scriptId) -> true);

        ImportClientsUseCase.ClientDto client1 = ClientHelper.createClient1ForImportClientsUseCase();
        ImportClientsUseCase.ClientDto client2 = ClientHelper.createClient2ForImportClientsUseCase();

        importClientsUseCase.execute(Arrays.asList(client1, client2));

        Mockito.verify(clientRepositoryGateway).save(Mockito.argThat(client-> client.getId().equals(client1.getId())));
        Mockito.verify(clientRepositoryGateway).save(Mockito.argThat(client-> client.getId().equals(client2.getId())));
    }

}