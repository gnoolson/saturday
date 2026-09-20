package gnoolson.saturday.app.repository.client;

import gnoolson.saturday.app.repository.client.entity.ClientEntity;
import gnoolson.saturday.app.repository.client.entity.ClientMapper;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ClientName;
import gnoolson.saturday.common.model.vo.ProjectId;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ClientRepositoryImpl implements ClientRepositoryGateway {

    private final ClientJPARepository clientJPARepository;

    /*
     *
     *
     * */
    @Cacheable(value = "client_find_by_id", key = "#id")
    @Override
    public Optional<Client> find(ClientId id) {
        Optional<ClientEntity> clientEntityOpt = clientJPARepository.findById(id.getValue());
        return clientEntityOpt.map(ClientMapper::toDomain);
    }

    @Override
    public List<Client> findEnabled() {
        Collection<ClientEntity> clients = clientJPARepository.findEnabled();
        return ClientMapper.toDomain(clients);
    }

    @Override
    public List<Client> findAll() {
        Iterable<ClientEntity> all = clientJPARepository.findAll();
        return ClientMapper.toDomain(all);
    }

    @Override
    public Optional<Client> find(ProjectId projectId, ClientName name) {
        Optional<ClientEntity> clientEntityOpt = clientJPARepository.find(projectId.getValue(), name.getValue());
        return clientEntityOpt.map(ClientMapper::toDomain);
    }

    @CacheEvict(value = "client_find_by_id", key = "#client.id")
    @Override
    public ClientId save(Client client) {
        ClientEntity result = clientJPARepository.save(ClientMapper.toJPA(client));
        return ClientId.of(result.getId());
    }

    @CacheEvict(value = "client_find_by_id", key = "#id")
    @Override
    public void delete(ClientId id) {
        clientJPARepository.delete(new ClientEntity(id.getValue()));
    }

}
