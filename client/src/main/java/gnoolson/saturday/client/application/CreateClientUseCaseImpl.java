package gnoolson.saturday.client.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.port.inbound.CreateClientUseCase;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.client.port.outbound.ProjectIdCheckerGateway;
import gnoolson.saturday.client.port.outbound.ScriptIdCheckerGateway;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ClientName;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class CreateClientUseCaseImpl implements CreateClientUseCase {

    private final ClientRepositoryGateway clientRepositoryGateway;
    private final Locker locker;
    private final TransactionStarter transactionStarter;
    private final ScriptIdCheckerGateway scriptIdCheckerGateway;
    private final ProjectIdCheckerGateway projectIdCheckerGateway;

    /*
     *
     *
     * */
    @Override
    public ClientId execute(ClientDto clientDto) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(clientDto.getName()),
                LockId.of(clientDto.getClientConnectionEventScriptHandler()),
                LockId.of(clientDto.getClientDisconnectionEventScriptHandler()),
                LockId.of(clientDto.getProjectId()))) {

            checkProject(clientDto.getProjectId());
            checkName(clientDto.getProjectId(), clientDto.getName());
            checkScript(clientDto.getProjectId(), clientDto.getClientConnectionEventScriptHandler());
            checkScript(clientDto.getProjectId(), clientDto.getClientDisconnectionEventScriptHandler());

            Client client = new Client(
                    clientDto.getProjectId(),
                    clientDto.getName(),
                    clientDto.getDescription(),
                    clientDto.getServerUri(),
                    clientDto.getClientAuth(),
                    clientDto.getClientConnectionEventScriptHandler(),
                    clientDto.getClientDisconnectionEventScriptHandler()
            );

            AtomicReference<ClientId> result = new AtomicReference<>();
            transactionStarter.doIt(() -> {
                ClientId clientId = clientRepositoryGateway.save(client);
                result.set(clientId);
            });

            return result.get();
        }
    }

    /*
     *
     *
     * */
    private void checkProject(ProjectId projectId) {
        if (!projectIdCheckerGateway.exists(projectId))
            throw new RuntimeException(String.format("Project \"%s\" was not found", projectId.getValue().toString())); // +
    }

    private void checkScript(ProjectId projectId, ScriptId scriptId) {
        if (scriptId.isEmpty())
            return;

        if (!scriptIdCheckerGateway.exists(projectId, scriptId))
            throw new RuntimeException(String.format("Script \"%s\" was not found", scriptId.getValue().toString())); // +
    }

    private void checkName(ProjectId projectId, ClientName name) {
        if (clientRepositoryGateway.find(projectId, name).isPresent())
            throw new RuntimeException(String.format("Client \"%s\" already exists in Project \"%s\" ", name.getValue(), projectId.getValue())); // +
    }

}
