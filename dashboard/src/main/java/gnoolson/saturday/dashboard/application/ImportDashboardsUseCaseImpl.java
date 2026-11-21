package gnoolson.saturday.dashboard.application;

import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.dashboard.model.entity.Dashboard;
import gnoolson.saturday.dashboard.model.vo.Html;
import gnoolson.saturday.dashboard.port.inbound.ImportDashboardsUseCase;
import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
import gnoolson.saturday.dashboard.port.outbound.ScriptIdCheckerGateway;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
public class ImportDashboardsUseCaseImpl implements ImportDashboardsUseCase {

    private final DashboardRepositoryGateway dashboardRepositoryGateway;
    private final ScriptIdCheckerGateway scriptIdCheckerGateway;

    /*
     *
     *
     * */
    @Override
    public void execute(List<DashboardDto> dashboards) {
        for (DashboardDto dashboard : dashboards) {
            save(dashboard);
        }
    }

    /*
     *
     *
     * */
    private void save(DashboardDto dashboardDto) {
        checkId(dashboardDto.getId());
        checkName(dashboardDto.getProjectId(), dashboardDto.getName());
        checkScript(dashboardDto.getProjectId(), dashboardDto.getScriptId());

        Dashboard dashboard = new Dashboard(
                dashboardDto.getId(),
                dashboardDto.getProjectId(),
                dashboardDto.getName(),
                dashboardDto.getDescription(),
                base64ToHtml(dashboardDto.getHtml()),
                dashboardDto.getScriptId(),
                dashboardDto.getAccess()
        );

        dashboardRepositoryGateway.save(dashboard);
    }

    private void checkScript(ProjectId projectId, ScriptId id) {
        if (!scriptIdCheckerGateway.exists(projectId, id))
            throw new RuntimeException(String.format("Script \"%s\" was not found", id.getValue().toString())); // +
    }

    private void checkName(ProjectId projectId, DashboardName name) {
        if (dashboardRepositoryGateway.find(projectId, name).isPresent())
            throw new RuntimeException(String.format("Dashboard \"%s\" already exists in Project \"%s\"", name.getValue(), projectId.getValue().toString())); // +
    }

    private void checkId(DashboardId id) {
        if (dashboardRepositoryGateway.find(id).isPresent())
            throw new RuntimeException(String.format("Dashboard \"%s\" already exists", id.getValue().toString())); // +
    }

    private Html base64ToHtml(Base64 base64){
        byte[] decode = java.util.Base64.getDecoder().decode(base64.getValue());
        return Html.of(new String(decode, StandardCharsets.UTF_8));
    }

}
