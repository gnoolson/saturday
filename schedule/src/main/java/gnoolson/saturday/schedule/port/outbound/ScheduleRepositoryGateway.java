package gnoolson.saturday.schedule.port.outbound;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScheduleId;
import gnoolson.saturday.common.model.vo.ScheduleName;
import gnoolson.saturday.schedule.model.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepositoryGateway {

    List<Schedule> findEnabled();

    List<Schedule> findAll();

    Optional<Schedule> find(ProjectId projectId, ScheduleName name);

    ScheduleId save(Schedule schedule);

    Optional<Schedule> find(ScheduleId id);

    void delete(ScheduleId id);

}
