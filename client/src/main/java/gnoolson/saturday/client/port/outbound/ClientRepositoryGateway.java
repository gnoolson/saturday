package gnoolson.saturday.client.port.outbound;

import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ClientName;
import gnoolson.saturday.common.model.vo.ProjectId;

import java.util.List;
import java.util.Optional;

public interface ClientRepositoryGateway {

    Optional<Client> find(ClientId id);

    ClientId save(Client client);

    List<Client> findEnabled();

    List<Client> findAll();

    Optional<Client> find(ProjectId projectId, ClientName name);

    void delete(ClientId clientId);

}
