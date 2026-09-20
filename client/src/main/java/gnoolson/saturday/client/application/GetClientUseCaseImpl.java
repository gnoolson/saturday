package gnoolson.saturday.client.application;

import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.model.exception.ClientNotFountException;
import gnoolson.saturday.client.port.inbound.GetClientUseCase;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.common.model.vo.ClientId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetClientUseCaseImpl implements GetClientUseCase {

    private final ClientRepositoryGateway clientRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public ClientDto execute(ClientId clientId) {
        Client client = clientRepositoryGateway.find(clientId).orElseThrow(() -> new ClientNotFountException(clientId));
        return new ClientDto(
                client.getId(),
                client.getProjectId(),
                client.getName(),
                client.getDescription(),
                client.getServerURI(),
                client.getClientAuth(),
                client.getConnectionError(),
                client.getConnectedEventHandler(),
                client.getDisconnectedEventHandler()
        );
    }

}
