package gnoolson.saturday.app.repository.script;

import gnoolson.saturday.app.repository.script.entity.ScriptEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScriptJPARepository extends CrudRepository<ScriptEntity, UUID> {

    @Query("from Script s where s.name = :name and s.project.id = :project_id")
    Optional<ScriptEntity> find(@Param("project_id") UUID project_id, @Param("name") String name);

    @Query("select s.id from Script s JOIN s.includedScripts WHERE included_script_id = :id")
    List<UUID> findOwners(@Param("id") String id); // dirty hack. With UUID get exception

    @Query("from Script s where s.id = :script_id and s.project.id = :project_id")
    Optional<ScriptEntity> find(@Param("project_id") UUID projectId, @Param("script_id") UUID scriptId);

    @Query("from Script s where s.autostart = true")
    Iterable<ScriptEntity> findAllAutostartScripts();

}
