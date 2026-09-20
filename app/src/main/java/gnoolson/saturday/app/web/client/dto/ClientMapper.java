package gnoolson.saturday.app.web.client.dto;

import gnoolson.saturday.client.model.vo.ClientAuth;
import gnoolson.saturday.client.model.vo.ServerURI;
import gnoolson.saturday.client.port.inbound.CreateClientUseCase;
import gnoolson.saturday.client.port.inbound.GetAllClientsUseCase;
import gnoolson.saturday.client.port.inbound.UpdateClientUseCase;
import gnoolson.saturday.common.model.vo.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ClientMapper {

    public static CreateClientUseCase.ClientDto toDomainDtoForCreateUseCase(ClientDto clientDto) {
        Description description = extractDescription(clientDto);
        ServerURI serverURI = extractServerURI(clientDto);
        ClientAuth clientAuth = extractClientAuth(clientDto);
        ClientName name = extractName(clientDto);
        return new CreateClientUseCase.ClientDto(
                name,
                ProjectId.of(clientDto.getProjectId()),
                description,
                serverURI,
                clientAuth,
                EmptyId.isEmpty(clientDto.getConnectionEventHandler()) ? ScriptId.empty() : ScriptId.of(clientDto.getConnectionEventHandler()),
                EmptyId.isEmpty(clientDto.getDisconnectionEventHandler()) ? ScriptId.empty() : ScriptId.of(clientDto.getDisconnectionEventHandler()));
    }

    public static UpdateClientUseCase.ClientDto toDomainForUpdateUseCase(ClientDto clientDto) {
        ClientId clientId = extractId(clientDto);
        ClientName name = extractName(clientDto);
        Description description = extractDescription(clientDto);
        ServerURI serverURI = extractServerURI(clientDto);
        ClientAuth clientAuth = extractClientAuth(clientDto);
        return new UpdateClientUseCase.ClientDto(
                clientId,
                ProjectId.of(clientDto.getProjectId()),
                name,
                description,
                serverURI,
                clientAuth,
                EmptyId.isEmpty(clientDto.getConnectionEventHandler()) ? ScriptId.empty() : ScriptId.of(clientDto.getConnectionEventHandler()),
                EmptyId.isEmpty(clientDto.getDisconnectionEventHandler()) ? ScriptId.empty() : ScriptId.of(clientDto.getDisconnectionEventHandler()));
    }

    public static Collection<ClientDto> toDto(List<GetAllClientsUseCase.ClientDto> clients) {
        return clients.stream().map(clientDto -> {
            return new ClientDto(clientDto);
        }).collect(Collectors.toList());
    }

    /*
     *
     *
     * */
    private static ClientId extractId(ClientDto clientDto) {
        return ClientId.of(clientDto.getId());
    }

    private static ClientAuth extractClientAuth(ClientDto clientDto) {
        if (clientDto.getAuth().isUse()) {
            AuthUsername authUsername = AuthUsername.of(clientDto.getAuth().getUsername());
            AuthPassword authPassword = AuthPassword.of(clientDto.getAuth().getPassword());
            return new ClientAuth(true, authUsername, authPassword);
        } else {
            return new ClientAuth(false, AuthUsername.empty(), AuthPassword.empty());
        }
    }

    private static ServerURI extractServerURI(ClientDto clientDto) {
        return ServerURI.of(clientDto.getServerURI());

    }

    private static ClientName extractName(ClientDto clientDto) {
        return ClientName.of(clientDto.getName());
    }

    private static Description extractDescription(ClientDto clientDto) {
        return Description.of(clientDto.getDescription());
    }

}
