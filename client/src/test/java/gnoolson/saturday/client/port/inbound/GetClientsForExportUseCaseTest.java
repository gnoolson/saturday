package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.client.ClientHelper;
import gnoolson.saturday.client.GeneralTest;
import gnoolson.saturday.client.application.GetClientsDataForExportUseCaseImpl;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;

import java.util.Arrays;
import java.util.List;

class GetClientsForExportUseCaseTest extends GeneralTest {

    @Test
    void general() {

        Client client1P1 = ClientHelper.createClient1_p1();
        Client client2P1 = ClientHelper.createClient2_p1();
        Client client1P2 = ClientHelper.createClient1_p2();
        Client client2P2 = ClientHelper.createClient2_p2();

        Client client1P3 = ClientHelper.createClient1_p3();
        Client client2P3 = ClientHelper.createClient2_p3();

        ClientRepositoryGateway clientRepositoryGateway = Mockito.mock(ClientRepositoryGateway.class);
        Mockito.when(clientRepositoryGateway.findAll()).thenReturn(Arrays.asList(
                client1P1, client2P1, client1P2, client2P2, client1P3, client2P3
        ));

        GetClientsForExportUseCase getClientsForExportUseCase = new GetClientsDataForExportUseCaseImpl(clientRepositoryGateway);

        List<GetClientsForExportUseCase.ClientDto> result = getClientsForExportUseCase.execute(Sets.newSet(client1P1.getProjectId(), client1P2.getProjectId()));

        Assertions.assertEquals(4, result.size());

        Assertions.assertTrue(result.stream().filter((dto) -> dto.getId().equals(client1P1.getId())).findFirst().isPresent());
        Assertions.assertTrue(result.stream().filter((dto) -> dto.getId().equals(client1P2.getId())).findFirst().isPresent());
        Assertions.assertTrue(result.stream().filter((dto) -> dto.getId().equals(client2P1.getId())).findFirst().isPresent());
        Assertions.assertTrue(result.stream().filter((dto) -> dto.getId().equals(client2P2.getId())).findFirst().isPresent());

    }

}