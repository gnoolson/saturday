package gnoolson.saturday.app.repository.schedule;

import gnoolson.saturday.app.repository.schedule.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleJPARepository extends CrudRepository<ScheduleEntity, UUID> {

    @Query("from Schedule s where s.enabled = true")
    List<ScheduleEntity> findEnabled();

    @Query("from Schedule s where s.name = :name and s.project.id = :project_id")
    Optional<ScheduleEntity> find(@Param("project_id") UUID project_id, @Param("name") String name);

}
