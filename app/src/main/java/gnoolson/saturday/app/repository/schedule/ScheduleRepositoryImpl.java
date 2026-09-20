package gnoolson.saturday.app.repository.schedule;

import gnoolson.saturday.app.repository.schedule.entity.ScheduleEntity;
import gnoolson.saturday.app.repository.schedule.entity.ScheduleMapper;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScheduleId;
import gnoolson.saturday.common.model.vo.ScheduleName;
import gnoolson.saturday.schedule.model.entity.Schedule;
import gnoolson.saturday.schedule.port.outbound.ScheduleRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ScheduleRepositoryImpl implements ScheduleRepositoryGateway {

    private final ScheduleJPARepository scheduleJPARepository;

    /*
     *
     *
     * */
    @Override
    public List<Schedule> findEnabled() {
        List<ScheduleEntity> entities = scheduleJPARepository.findEnabled();
        return entities.stream().map(ScheduleMapper::toDomain).collect(Collectors.toList());
    }


    @Override
    public List<Schedule> findAll() {
        Iterable<ScheduleEntity> entities = scheduleJPARepository.findAll();
        return ScheduleMapper.toDomain(entities);
    }


    @Override
    public Optional<Schedule> find(ProjectId projectId, ScheduleName name) {
        Optional<ScheduleEntity> byName = scheduleJPARepository.find(projectId.getValue(), name.getValue());
        return byName.map(ScheduleMapper::toDomain);
    }


    @Override
    public Optional<Schedule> find(ScheduleId id) {
        Optional<ScheduleEntity> byId = scheduleJPARepository.findById(id.getValue());
        return byId.map(ScheduleMapper::toDomain);
    }


    @Override
    public ScheduleId save(Schedule schedule) {
        ScheduleEntity entity = ScheduleMapper.toJPA(schedule);
        scheduleJPARepository.save(entity);
        return ScheduleId.of(entity.getId());
    }


    @Override
    public void delete(ScheduleId id) {
        scheduleJPARepository.deleteById(id.getValue());
    }

}
