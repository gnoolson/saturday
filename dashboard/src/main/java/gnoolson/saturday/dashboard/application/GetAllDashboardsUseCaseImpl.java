package gnoolson.saturday.dashboard.application;

import gnoolson.saturday.dashboard.model.entity.Dashboard;
import gnoolson.saturday.dashboard.port.inbound.GetAllDashboardsUseCase;
import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class GetAllDashboardsUseCaseImpl implements GetAllDashboardsUseCase {

    private final DashboardRepositoryGateway dashboardRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public List<DashboardDto> execute() {
        List<Dashboard> dashboards;
        dashboards = dashboardRepositoryGateway.findAll();

        return dashboards.stream().map(dashboard -> {
            return new DashboardDto(
                    dashboard.getId(),
                    dashboard.getProjectId(),
                    dashboard.getName(),
                    dashboard.getDescription(),
                    dashboard.getAccess()
            );
        }).collect(Collectors.toList());
    }

}
