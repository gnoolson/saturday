package gnoolson.saturday.client.application;

import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.model.vo.ClientAuth;
import gnoolson.saturday.client.model.vo.ConnectionError;
import gnoolson.saturday.client.model.vo.ServerURI;
import gnoolson.saturday.client.port.outbound.ClientConnectedScriptLauncherGateway;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.common.time.vo.TimeInMs;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class ClientConnectedEventHandlerTest {

    @Test
    void general(){
        Client client = new Client(
                ClientId.random(),
                ProjectId.random(),
                ClientName.random(),
                Description.empty(),
                true,
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                TimeInMs.zero(),
                TimeInMs.zero(),
                ConnectionError.empty(),
                ScriptId.random(),
                ScriptId.empty()
        );

        ClientRepositoryGateway clientRepositoryGateway = Mockito.mock(ClientRepositoryGateway.class);
        Mockito.when(clientRepositoryGateway.find(client.getId())).thenReturn(Optional.of(client));

        ClientConnectedScriptLauncherGateway clientConnectedScriptLauncherGateway = Mockito.mock(ClientConnectedScriptLauncherGateway.class);

        ClientConnectedEventHandler clientConnectedEventHandler = new ClientConnectedEventHandler(clientRepositoryGateway, clientConnectedScriptLauncherGateway, (projectId, scriptId) -> true);
        clientConnectedEventHandler.execute(client.getId());


        Mockito.verify(clientConnectedScriptLauncherGateway).execute(client.getConnectedEventHandler(), client.getId());
    }

}