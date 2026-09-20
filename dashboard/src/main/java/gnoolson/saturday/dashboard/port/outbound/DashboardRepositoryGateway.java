package gnoolson.saturday.dashboard.port.outbound;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.DashboardName;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.dashboard.model.entity.Dashboard;

import java.util.List;
import java.util.Optional;

public interface DashboardRepositoryGateway {

    Optional<Dashboard> find(DashboardId id);

    List<Dashboard> findAll();

    DashboardId save(Dashboard dashboard);

    Optional<Dashboard> find(ProjectId projectId, DashboardName name);

    void delete(DashboardId id);

}

