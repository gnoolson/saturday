package gnoolson.saturday.app.repository.client.entity;

import gnoolson.saturday.app.repository.project.entity.ProjectEntity;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.model.vo.ClientAuth;
import gnoolson.saturday.client.model.vo.ConnectionError;
import gnoolson.saturday.client.model.vo.ServerURI;
import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.common.time.vo.TimeInMs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientMapper {

    public static Client toDomain(ClientEntity clientEntity) {

        ConnectionErrorDto connectionErrorDto = ConnectionErrorDto.fromJSON(clientEntity.getConnectionError());

        return new Client(
                ClientId.of(clientEntity.getId()),
                ProjectId.of(clientEntity.getProject().getId()),
                ClientName.of(clientEntity.getName()),
                Description.of(clientEntity.getDescription()),
                clientEntity.isEnabled(),
                ServerURI.of(clientEntity.getServerURI()),
                new ClientAuth(clientEntity.isUseAuth(), AuthUsername.of(clientEntity.getAuthUsername()), AuthPassword.of(clientEntity.getAuthPassword())),
                TimeInMs.of(clientEntity.getConnectedAt()),
                TimeInMs.of(clientEntity.getDisconnectedAt()),
                ConnectionError.of(connectionErrorDto.getMessage(), TimeInMs.of(connectionErrorDto.getTimestamp())),
                ScriptId.of(clientEntity.getConnectedEventHandler()),
                ScriptId.of(clientEntity.getDisconnectedEventHandler())
        );
    }

    public static ClientEntity toJPA(Client client) {

        ConnectionErrorDto connectionErrorDto = new ConnectionErrorDto(client.getConnectionError());

        return new ClientEntity(
                client.getId().isEmpty() ? UUID.randomUUID() : client.getId().getValue(),
                new ProjectEntity(client.getProjectId().getValue()),
                client.getName().getValue(),
                client.getDescription().getValue(),
                client.isEnabled(),
                client.getServerURI().getValue(),
                client.getClientAuth().isUse(),
                client.getClientAuth().getUsername().getValue(),
                client.getClientAuth().getPassword().getValue(),
                client.getConnectedAt().getValue(),
                client.getDisconnectedAt().getValue(),
                connectionErrorDto.toJSON(),
                new ArrayList<>(0),
                client.getConnectedEventHandler().getValue(),
                client.getDisconnectedEventHandler().getValue()
        );
    }

    public static List<Client> toDomain(Iterable<ClientEntity> all) {
        List<Client> result = new ArrayList<>();
        all.forEach((clientEntity) -> {
            result.add(toDomain(clientEntity));
        });

        return result;
    }

}
