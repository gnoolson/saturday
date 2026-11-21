package gnoolson.saturday.client.application;

import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.port.inbound.RefreshEnabledClientsUseCase;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RefreshEnabledClientsUseCaseImpl implements RefreshEnabledClientsUseCase {

    private final ClientRepositoryGateway clientRepositoryGateway;
    private final MQTTClientManager MQTTClientManager;

    /*
     *
     *
     * */
    @Override
    public void execute() {
        List<Client> enabledClients = clientRepositoryGateway.findEnabled();
        MQTTClientManager.syncClients(enabledClients);
    }

}
