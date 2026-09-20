package gnoolson.saturday.client.application;

import gnoolson.saturday.client.ClientHelper;
import gnoolson.saturday.client.GeneralTest;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.common.time.TimeProvider;
import gnoolson.saturday.common.time.vo.TimeInMs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class MarkConnectedTest extends GeneralTest {

    @Test
    void general(){
        Client client1P1 = ClientHelper.createClient1_p1();
        ClientRepositoryGateway clientRepositoryGateway = Mockito.mock(ClientRepositoryGateway.class);
        Mockito.when(clientRepositoryGateway.find(client1P1.getId())).thenReturn(Optional.of(client1P1));

        MarkConnected markConnected = new MarkConnected(
                clientRepositoryGateway,
                new TimeProvider() {
                    @Override
                    public TimeInMs now() {
                        return TimeInMs.of(999);
                    }
                },
                locker,
                transactionStarter
        );

        markConnected.exec(client1P1.getId());

        Assertions.assertEquals(TimeInMs.of(999), client1P1.getConnectedAt());

        Mockito.verify(clientRepositoryGateway).save(Mockito.argThat(client->{
            return client.getId().equals(client1P1.getId());
        }));
    }

}