package gnoolson.saturday.broker.application;

import gnoolson.saturday.broker.port.outbount.ConnectedClientsProviderGateway;
import gnoolson.saturday.common.model.vo.AuthUsername;
import gnoolson.saturday.common.model.vo.ClientName;
import gnoolson.saturday.common.time.vo.TimeInMs;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ConnectedClientsProviderGatewayImpl implements ConnectedClientsProviderGateway {

    private final ClientSessionStorage clientSessionStorage;

    /*
    *
    *
    * */
    @Override
    public List<ConnectedClientsProviderGateway.ClientDto> execute() {
        return clientSessionStorage.getAll().stream().map(clientSession -> new ConnectedClientsProviderGateway.ClientDto(
                ClientName.of(clientSession.getClientName()),
                AuthUsername.of(clientSession.getUsername()),
                TimeInMs.of(clientSession.getConnected())
        )).collect(Collectors.toList());
    }
    
}
