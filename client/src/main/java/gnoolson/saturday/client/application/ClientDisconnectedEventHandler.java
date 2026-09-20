package gnoolson.saturday.client.application;

import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.port.outbound.ClientDisconnectedScriptLauncherGateway;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.client.port.outbound.ScriptIdCheckerGateway;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ScriptId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

// +
@Log4j2
@RequiredArgsConstructor
public class ClientDisconnectedEventHandler {

    private final ClientRepositoryGateway clientRepositoryGateway;
    private final ClientDisconnectedScriptLauncherGateway clientDisconnectedScriptLauncherGateway;
    private final ScriptIdCheckerGateway scriptIdCheckerGateway;

    /*
     *
     *
     * */
    public void execute(ClientId clientId) {
        Optional<Client> clientOpt = clientRepositoryGateway.find(clientId);
        if (!clientOpt.isPresent()) {
            if(log.isDebugEnabled())
                log.debug("Client \"{}\" was not found. Maybe it was deleted.", clientId.getValue().toString());

            return;
        }

        Client client = clientOpt.get();
        ScriptId disconnectionEventHandler = client.getDisconnectedEventHandler();
        if (disconnectionEventHandler.isEmpty())
            return;

        if (!scriptIdCheckerGateway.exists(client.getProjectId(), disconnectionEventHandler))
            return;

        clientDisconnectedScriptLauncherGateway.execute(disconnectionEventHandler, clientId);
    }

}
