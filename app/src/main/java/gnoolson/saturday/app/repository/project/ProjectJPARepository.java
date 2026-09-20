package gnoolson.saturday.app.repository.project;

import gnoolson.saturday.app.repository.project.entity.ProjectEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProjectJPARepository extends CrudRepository<ProjectEntity, UUID> {

    @Query("from Project p where p.name = :name")
    Optional<ProjectEntity> findByName(@Param("name") String name);

}
