package gnoolson.saturday.dashboard.application;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.dashboard.model.entity.Dashboard;
import gnoolson.saturday.dashboard.port.inbound.GetDashboardsForExportUseCase;
import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class GetDashboardsForExportUseCaseImpl implements GetDashboardsForExportUseCase {

    private final DashboardRepositoryGateway dashboardRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public List<DashboardDto> execute(Set<ProjectId> projectIdSet) {
        List<Dashboard> dashboards = dashboardRepositoryGateway.findAll();

        return dashboards.stream()
                .filter(dashboard -> {
                    return projectIdSet.contains(dashboard.getProjectId());
                })
                .map(dashboard -> {
                    return new DashboardDto(
                            dashboard.getId(),
                            dashboard.getProjectId(),
                            dashboard.getName(),
                            dashboard.getDescription(),
                            dashboard.getHtml(),
                            dashboard.getScriptId(),
                            dashboard.getAccess()
                    );
                }).collect(Collectors.toList());
    }

}
