package gnoolson.saturday.dashboard.application;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.DashboardName;
import gnoolson.saturday.dashboard.model.entity.Dashboard;
import gnoolson.saturday.dashboard.model.exception.DashboardNotFoundException;
import gnoolson.saturday.dashboard.port.inbound.GetDashboardNameUseCase;
import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class GetDashboardNameUseCaseImpl implements GetDashboardNameUseCase {

    private final DashboardRepositoryGateway dashboardRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public DashboardName execute(DashboardId id) {
        Optional<Dashboard> dashboardOptional = dashboardRepositoryGateway.find(id);
        Dashboard dashboard = dashboardOptional.orElseThrow(() -> new DashboardNotFoundException(id));
        return dashboard.getName();
    }

}
