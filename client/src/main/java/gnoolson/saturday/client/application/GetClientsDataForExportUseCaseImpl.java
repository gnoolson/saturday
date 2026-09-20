package gnoolson.saturday.client.application;

import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.port.inbound.GetClientsForExportUseCase;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.common.model.vo.ProjectId;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetClientsDataForExportUseCaseImpl implements GetClientsForExportUseCase {

    private final ClientRepositoryGateway clientRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public List<ClientDto> execute(Set<ProjectId> projectIdSet) {
        List<Client> allClients = clientRepositoryGateway.findAll();

        return allClients.stream()
                .filter(client -> {
                    return projectIdSet.contains(client.getProjectId());
                })
                .map(client -> new ClientDto(
                        client.getId(),
                        client.getProjectId(),
                        client.getName(),
                        client.getDescription(),
                        client.getServerURI(),
                        client.getClientAuth(),
                        client.getConnectedEventHandler(),
                        client.getDisconnectedEventHandler()
                )).collect(Collectors.toList());
    }

}
