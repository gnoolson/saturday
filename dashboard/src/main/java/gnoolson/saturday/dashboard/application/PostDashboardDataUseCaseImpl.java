package gnoolson.saturday.dashboard.application;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.OpenDashboardId;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.dashboard.model.entity.Dashboard;
import gnoolson.saturday.dashboard.model.exception.DashboardNotFoundException;
import gnoolson.saturday.dashboard.model.exception.NotAuthorized;
import gnoolson.saturday.dashboard.port.inbound.CheckAuthUseCase;
import gnoolson.saturday.dashboard.port.inbound.PostDashboardDataUseCase;
import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
import gnoolson.saturday.dashboard.port.outbound.DashboardScriptLauncherGateway;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class PostDashboardDataUseCaseImpl implements PostDashboardDataUseCase {

    private final DashboardRepositoryGateway dashboardRepositoryGateway;
    private final DashboardScriptLauncherGateway dashboardScriptLauncherGateway;
    private final CheckAuthUseCase checkAuthUseCase;

    /*
     *
     *
     * */
    @Override
    public void execute(DashboardId id, OpenDashboardId openDashboardId, Map<String, Object> actionData, Role role) throws NotAuthorized {
        if (!checkAuthUseCase.execute(id, role))
            throw new NotAuthorized(); // +

        Dashboard dashboard = dashboardRepositoryGateway.find(id).orElseThrow(() -> new DashboardNotFoundException(id));
        dashboardScriptLauncherGateway.execute(dashboard.getScriptId(), dashboard.getId(), openDashboardId, actionData);
    }

}
