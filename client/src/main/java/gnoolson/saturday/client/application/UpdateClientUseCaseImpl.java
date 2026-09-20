package gnoolson.saturday.client.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.model.exception.ClientNotFountException;
import gnoolson.saturday.client.port.inbound.UpdateClientUseCase;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.client.port.outbound.ScriptIdCheckerGateway;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ClientName;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateClientUseCaseImpl implements UpdateClientUseCase {

    private final Locker locker;
    private final ClientRepositoryGateway clientRepositoryGateway;
    private final TransactionStarter transactionStarter;
    private final MQTTClientManager mqttClientManager;
    private final ScriptIdCheckerGateway scriptIdCheckerGateway;

    /*
     *
     *
     * */
    @Override
    public void execute(ClientDto clientDto) {
        Client client = clientRepositoryGateway.find(clientDto.getId()).orElseThrow(() -> new ClientNotFountException(clientDto.getId()));

        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(clientDto.getName()), LockId.of(client.getName()), LockId.of(client.getId()), LockId.of(clientDto.getClientConnectionEventScriptHandler()), LockId.of(clientDto.getClientDisconnectionEventScriptHandler()), LockId.of(client.getProjectId()))) {

            checkName(client.getProjectId(), client.getName(), clientDto.getName());
            checkScript(client.getProjectId(), clientDto.getClientConnectionEventScriptHandler());
            checkScript(client.getProjectId(), clientDto.getClientDisconnectionEventScriptHandler());

            client.update(clientDto.getName(), clientDto.getDescription(), clientDto.getServerUri(), clientDto.getClientAuth(), clientDto.getClientConnectionEventScriptHandler(), clientDto.getClientDisconnectionEventScriptHandler());
            client.eraseConnectionError();

            if (client.isEnabled()) {
                client.disable();
                if(mqttClientManager.isConnected(client.getId()))
                    mqttClientManager.disconnect(client.getId());
            }

            transactionStarter.doIt(() -> {
                clientRepositoryGateway.save(client);
            });
        }
    }

    /*
     *
     *
     * */
    private void checkName(ProjectId projectId, ClientName name, ClientName newName) {
        if (name.equals(newName)) return;

        if (clientRepositoryGateway.find(projectId, newName).isPresent())
            throw new RuntimeException(String.format("Client \"%s\" already exists in Project \"%s\"", newName.getValue(), projectId.getValue())); // +
    }

    private void checkScript(ProjectId projectId, ScriptId scriptId) {
        if (scriptId.isEmpty()) return;

        if (!scriptIdCheckerGateway.exists(projectId, scriptId))
            throw new RuntimeException(String.format("Script \"%s\" was not found", scriptId.getValue().toString())); // +
    }

}
