package gnoolson.saturday.app.repository.dashboard;

import gnoolson.saturday.app.repository.dashboard.entity.DashboardEntity;
import gnoolson.saturday.app.repository.dashboard.entity.DashboardMapper;
import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.DashboardName;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.dashboard.model.entity.Dashboard;
import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class DashboardRepositoryGatewayImpl implements DashboardRepositoryGateway {

    private final DashboardJPARepository dashboardJPARepository;

    /*
     *
     *
     * */
    @Cacheable(value = "dashboard_find_by_id", key = "#id")
    @Override
    public Optional<Dashboard> find(DashboardId id) {
        Optional<DashboardEntity> entityOpt = dashboardJPARepository.findById(id.getValue());

        return entityOpt.map(DashboardMapper::toDomain);
    }


    @Override
    public List<Dashboard> findAll() {
        Iterable<DashboardEntity> all = dashboardJPARepository.findAll();

        List<Dashboard> result = new ArrayList<>();
        for (DashboardEntity entity : all) {
            result.add(DashboardMapper.toDomain(entity));
        }
        return result;
    }


    @Override
    public Optional<Dashboard> find(ProjectId projectId, DashboardName name) {
        Optional<DashboardEntity> dashboardEntityOpt = dashboardJPARepository.find(projectId.getValue(), name.getValue());
        return dashboardEntityOpt.map(DashboardMapper::toDomain);
    }

    @CacheEvict(value = "dashboard_find_by_id", key = "#dashboard.id")
    @Override
    public DashboardId save(Dashboard dashboard) {
        DashboardEntity dashboardEntity = DashboardMapper.toJPA(dashboard);
        dashboardJPARepository.save(dashboardEntity);
        return DashboardId.of(dashboardEntity.getId());
    }

    @CacheEvict(value = "dashboard_find_by_id", key = "#id")
    @Override
    public void delete(DashboardId id) {
        dashboardJPARepository.deleteById(id.getValue());
    }

}
