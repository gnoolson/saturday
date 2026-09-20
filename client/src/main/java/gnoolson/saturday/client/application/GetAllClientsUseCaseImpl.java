package gnoolson.saturday.client.application;

import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.port.inbound.GetAllClientsUseCase;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class GetAllClientsUseCaseImpl implements GetAllClientsUseCase {

    private final ClientRepositoryGateway clientRepositoryGateway;
    private final MQTTClientManager mqttClientManager;

    /*
     *
     *
     * */
    @Override
    public List<ClientDto> execute() {
        List<Client> clients = clientRepositoryGateway.findAll();
        List<ClientDto> result = new ArrayList<>(clients.size());

        for (Client client : clients) {
            ClientDto clientDto = new ClientDto(
                    client.getId(),
                    client.getProjectId(),
                    client.getName(),
                    client.getDescription(),
                    client.getServerURI(),
                    getConnectionStatus(client),
                    client.getConnectionError(),
                    client.getConnectedAt(),
                    client.getDisconnectedAt()
            );
            result.add(clientDto);
        }

        return result;
    }

    /*
     *
     *
     * */
    public ConnectionStatus getConnectionStatus(Client client) {
        boolean flag = mqttClientManager.isConnected(client.getId());

        if (flag) {
            if (client.isEnabled())
                return ConnectionStatus.CONNECTED;

            return ConnectionStatus.DISCONNECTION;
        } else {
            if (client.isEnabled())
                return ConnectionStatus.CONNECTION;

            return ConnectionStatus.DISCONNECTED;
        }
    }

}
