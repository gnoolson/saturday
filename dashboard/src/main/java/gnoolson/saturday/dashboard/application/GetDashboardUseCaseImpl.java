package gnoolson.saturday.dashboard.application;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.dashboard.model.entity.Dashboard;
import gnoolson.saturday.dashboard.model.exception.DashboardNotFoundException;
import gnoolson.saturday.dashboard.port.inbound.GetDashboardUseCase;
import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetDashboardUseCaseImpl implements GetDashboardUseCase {

    private final DashboardRepositoryGateway dashboardRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public DashboardDto execute(DashboardId id) {
        Dashboard dashboard = dashboardRepositoryGateway.find(id).orElseThrow(() -> new DashboardNotFoundException(id));
        return new DashboardDto(
                dashboard.getId(),
                dashboard.getProjectId(),
                dashboard.getName(),
                dashboard.getDescription(),
                dashboard.getHtml(),
                dashboard.getScriptId(),
                dashboard.getAccess()
        );
    }

}
