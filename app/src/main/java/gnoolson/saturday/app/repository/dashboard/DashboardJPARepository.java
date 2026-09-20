package gnoolson.saturday.app.repository.dashboard;

import gnoolson.saturday.app.repository.dashboard.entity.DashboardEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DashboardJPARepository extends CrudRepository<DashboardEntity, UUID> {

    @Query("from Dashboard d where d.name = :name and d.project.id = :project_id")
    Optional<DashboardEntity> find(@Param("project_id") UUID projectId, @Param("name") String name);

    @Query("from Dashboard d where d.access = 'ANONYMOUS'")
    Iterable<DashboardEntity> findAllWithAnonymousAccess();

}
