package gnoolson.saturday.dashboard.application;

import gnoolson.saturday.common.model.vo.Access;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.dashboard.model.entity.Dashboard;
import gnoolson.saturday.dashboard.port.inbound.GetAllDashboardsForRootPageUseCase;
import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetAllDashboardsForRootPageUseCaseImpl implements GetAllDashboardsForRootPageUseCase {

    private final DashboardRepositoryGateway dashboardRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public List<DashboardDto> execute(Role role) {
        List<Dashboard> dashboards = dashboardRepositoryGateway.findAll().stream().filter((dashboard) -> {
            if (role == Role.EDITOR)
                return true;

            if (role == Role.VIEWER && (dashboard.getAccess() == Access.VIEWER || dashboard.getAccess() == Access.ANONYMOUS))
                return true;

            if (role == Role.ANONYMOUS && dashboard.getAccess() == Access.ANONYMOUS)
                return true;

            return false;

        }).collect(Collectors.toList());

        return dashboards.stream().map((dashboard) -> {
            return new DashboardDto(dashboard.getId(),
                    dashboard.getProjectId(),
                    dashboard.getName(),
                    dashboard.getDescription()
            );
        }).collect(Collectors.toList());
    }

}
