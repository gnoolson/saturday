package gnoolson.saturday.dashboard.application;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.dashboard.model.entity.Dashboard;
import gnoolson.saturday.dashboard.model.exception.DashboardNotFoundException;
import gnoolson.saturday.dashboard.model.exception.NotAuthorized;
import gnoolson.saturday.dashboard.model.vo.Html;
import gnoolson.saturday.dashboard.port.inbound.CheckAuthUseCase;
import gnoolson.saturday.dashboard.port.inbound.GetDashboardHtmlUseCase;
import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class GetDashboardHtmlUseCaseImpl implements GetDashboardHtmlUseCase {

    private final DashboardRepositoryGateway dashboardRepositoryGateway;
    private final CheckAuthUseCase checkAuthUseCase;

    /*
     *
     *
     * */
    @Override
    public Html execute(DashboardId id, Role role) throws NotAuthorized {
        if (!checkAuthUseCase.execute(id, role))
            throw new NotAuthorized(); // +

        Optional<Dashboard> dashboardOpt = dashboardRepositoryGateway.find(id);

        UUID openDashboardId = UUID.randomUUID();
        Dashboard dashboard = dashboardOpt.orElseThrow(() -> new DashboardNotFoundException(id));

        return prepareHtmlForView(dashboard, openDashboardId);
    }

    /*
     *
     *
     * */
    private Html prepareHtmlForView(Dashboard dashboard, UUID openDashboardId) {
        String value = dashboard.getHtml().getValue();

        StringBuilder result = new StringBuilder();
        result.append("<script>window.dashboardId = \"");
        result.append(dashboard.getId().getValue().toString());
        result.append("\";window.openDashboardId=\"");
        result.append(openDashboardId.toString());
        result.append("\";</script>");
        result.append(value);

        return Html.of(result.toString());
    }

}
