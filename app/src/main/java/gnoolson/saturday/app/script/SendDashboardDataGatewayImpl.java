package gnoolson.saturday.app.script;

import gnoolson.saturday.common.model.vo.OpenDashboardId;
import gnoolson.saturday.dashboard.application.OpenDashboardsService;
import gnoolson.saturday.dashboard.model.vo.DashboardResponse;
import gnoolson.saturday.internal_lua_libs.dashboard.OutgoingMessage;
import gnoolson.saturday.internal_lua_libs.dashboard.SendDashboardDataGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendDashboardDataGatewayImpl implements SendDashboardDataGateway, gnoolson.saturday.internal_lua_libs.open_dashboard.SendDashboardDataGateway {

    private final OpenDashboardsService openDashboardsService;

    /*
     *
     *
     * */
    @Override
    public void execute(OpenDashboardId openDashboardId, OutgoingMessage outgoingMessage) {
        openDashboardsService.send(openDashboardId, DashboardResponse.of(outgoingMessage.getData()));
    }

    @Override
    public boolean execute(OpenDashboardId openDashboardId, gnoolson.saturday.internal_lua_libs.open_dashboard.OutgoingMessage outgoingMessage) {
        return openDashboardsService.send(openDashboardId, DashboardResponse.of(outgoingMessage.getData()));
    }

}
