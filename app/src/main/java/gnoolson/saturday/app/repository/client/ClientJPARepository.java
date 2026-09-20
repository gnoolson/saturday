package gnoolson.saturday.app.repository.client;

import gnoolson.saturday.app.repository.client.entity.ClientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ClientJPARepository extends CrudRepository<ClientEntity, UUID> {

    @Query("from Client cl where cl.enabled = true")
    Collection<ClientEntity> findEnabled();

    @Query("from Client cl where cl.name = :name and cl.project.id = :project_id")
    Optional<ClientEntity> find(@Param("project_id") UUID projectId, @Param("name") String name);

}
