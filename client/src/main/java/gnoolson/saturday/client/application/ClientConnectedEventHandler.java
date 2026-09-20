package gnoolson.saturday.client.application;

import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.model.exception.ClientNotFountException;
import gnoolson.saturday.client.port.outbound.ClientConnectedScriptLauncherGateway;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.client.port.outbound.ScriptIdCheckerGateway;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ScriptId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

// +
@Log4j2
@RequiredArgsConstructor
public class ClientConnectedEventHandler {

    private final ClientRepositoryGateway clientRepositoryGateway;
    private final ClientConnectedScriptLauncherGateway clientConnectedScriptLauncherGateway;
    private final ScriptIdCheckerGateway scriptIdCheckerGateway;

    /*
     *
     *
     * */
    public void execute(ClientId clientId) {
        Client client = clientRepositoryGateway.find(clientId).orElseThrow(() -> new ClientNotFountException(clientId));
        ScriptId connectionEventHandler = client.getConnectedEventHandler();

        if (connectionEventHandler.isEmpty())
            return;

        if (!scriptIdCheckerGateway.exists(client.getProjectId(), connectionEventHandler))
            return;

        clientConnectedScriptLauncherGateway.execute(connectionEventHandler, clientId);
    }

}
