package gnoolson.saturday.app.subscription;

import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.model.exception.ClientNotFountException;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.subscription.port.outbound.GetClientProjectIdGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class GetClientProjectIdGatewayImpl implements GetClientProjectIdGateway {

    private final ClientRepositoryGateway clientRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public ProjectId execute(ClientId clientId) {
        Optional<Client> clientOpt = clientRepositoryGateway.find(clientId);
        return clientOpt.orElseThrow(() -> {
            return new ClientNotFountException(clientId);
        }).getProjectId();
    }

}
