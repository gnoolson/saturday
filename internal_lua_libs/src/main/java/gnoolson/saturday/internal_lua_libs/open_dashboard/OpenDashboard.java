package gnoolson.saturday.internal_lua_libs.open_dashboard;

import gnoolson.saturday.common.model.vo.DashboardName;
import gnoolson.saturday.common.model.vo.OpenDashboardId;
import gnoolson.saturday.common.model.vo.ProjectId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OpenDashboard {

    private final OpenDashboardId openDashboardId;
    private final ProjectId projectId;
    private final DashboardName name;
    private final SendDashboardDataGateway sendDashboardDataGateway;

    /*
     *
     *
     * */
    public boolean send(OutgoingMessage outgoingMessage) {
        return sendDashboardDataGateway.execute(openDashboardId, outgoingMessage);
    }

}
