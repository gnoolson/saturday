package gnoolson.saturday.client.application;

import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.model.vo.ConnectionError;
import gnoolson.saturday.client.port.inbound.ImportClientsUseCase;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.client.port.outbound.ScriptIdCheckerGateway;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ClientName;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.time.vo.TimeInMs;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ImportClientsUseCaseImpl implements ImportClientsUseCase {

    private final ClientRepositoryGateway clientRepositoryGateway;
    private final ScriptIdCheckerGateway scriptIdCheckerGateway;

    /*
     *
     *
     * */
    @Override
    public void execute(List<ClientDto> clients) {
        for (ClientDto client : clients) {
            save(client);
        }
    }

    /*
     *
     *
     * */
    private void save(ClientDto clientDto) {
        checkId(clientDto.getId());
        checkName(clientDto.getProjectId(), clientDto.getName());
        checkScript(clientDto.getProjectId(), clientDto.getClientConnectionEventScriptHandler());
        checkScript(clientDto.getProjectId(), clientDto.getClientDisconnectionEventScriptHandler());

        Client client = new Client(
                clientDto.getId(),
                clientDto.getProjectId(),
                clientDto.getName(),
                clientDto.getDescription(),
                false,
                clientDto.getServerURI(),
                clientDto.getClientAuth(),
                TimeInMs.zero(),
                TimeInMs.zero(),
                ConnectionError.empty(),
                clientDto.getClientConnectionEventScriptHandler(),
                clientDto.getClientDisconnectionEventScriptHandler()
        );

        clientRepositoryGateway.save(client);
    }

    private void checkName(ProjectId projectId, ClientName name) {
        if (clientRepositoryGateway.find(projectId, name).isPresent())
            throw new RuntimeException(String.format("Client \"%s\" already exists in Project \"%s\"", name.getValue(), projectId.getValue().toString())); // +
    }

    private void checkId(ClientId id) {
        if (clientRepositoryGateway.find(id).isPresent())
            throw new RuntimeException(String.format("Client \"%s\" already exists", id.getValue().toString())); // +
    }

    private void checkScript(ProjectId projectId, ScriptId scriptId) {
        if (scriptId.isEmpty())
            return;

        if (!scriptIdCheckerGateway.exists(projectId, scriptId))
            throw new RuntimeException(String.format("Script \"%s\" was not found", scriptId.getValue().toString())); // +
    }

}
