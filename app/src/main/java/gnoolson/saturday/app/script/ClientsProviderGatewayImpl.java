package gnoolson.saturday.app.script;

import gnoolson.saturday.client.application.MQTTClientManager;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.internal_lua_libs.client_info.Client;
import gnoolson.saturday.internal_lua_libs.client_info.ClientsInProjectProviderGateway;
import gnoolson.saturday.internal_lua_libs.client_info.URI;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ClientsProviderGatewayImpl implements ClientsInProjectProviderGateway {

    private final MQTTClientManager mqttClientManager;
    private final ClientRepositoryGateway clientRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public List<Client> execute(ProjectId projectId) {
        List<gnoolson.saturday.client.model.entity.Client> allClients = clientRepositoryGateway.findAll();
        List<Client> result = new ArrayList<>(allClients.size());

        for (gnoolson.saturday.client.model.entity.Client client : allClients) {
            if (!client.getProjectId().equals(projectId))
                continue;

            result.add(
                    new Client(
                            client.getId(),
                            client.getName(),
                            client.getProjectId(),
                            mqttClientManager.isConnected(client.getId()),
                            URI.of(client.getServerURI().getValue()))
            );
        }

        return result;
    }

}
