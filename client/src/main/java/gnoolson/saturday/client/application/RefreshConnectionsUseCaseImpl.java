package gnoolson.saturday.client.application;

import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.port.inbound.RefreshConnectionsUseCase;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RefreshConnectionsUseCaseImpl implements RefreshConnectionsUseCase {

    private final ClientRepositoryGateway clientRepositoryGateway;
    private final MQTTClientManager MQTTClientManager;

    /*
     *
     *
     * */
    @Override
    public void execute() {
        List<Client> enabledClients = clientRepositoryGateway.findEnabled();
        MQTTClientManager.syncConnections(enabledClients);
    }

}
