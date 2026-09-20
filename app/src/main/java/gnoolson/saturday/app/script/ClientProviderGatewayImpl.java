package gnoolson.saturday.app.script;

import gnoolson.saturday.client.application.MQTTClientManager;
import gnoolson.saturday.client.model.exception.ClientNotFountException;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.internal_lua_libs.client_info.Client;
import gnoolson.saturday.internal_lua_libs.client_info.ClientProviderGateway;
import gnoolson.saturday.internal_lua_libs.client_info.URI;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientProviderGatewayImpl implements ClientProviderGateway {

    private final MQTTClientManager mqttClientManager;
    private final ClientRepositoryGateway clientRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public Client execute(ClientId clientId) {
        gnoolson.saturday.client.model.entity.Client client = clientRepositoryGateway.find(clientId).orElseThrow(() -> new ClientNotFountException(clientId));

        return new Client(
                client.getId(),
                client.getName(),
                client.getProjectId(),
                mqttClientManager.isConnected(client.getId()),
                URI.of(client.getServerURI().getValue()));
    }

}
