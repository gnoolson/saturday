package gnoolson.saturday.internal_lua_libs.dashboard;

import gnoolson.saturday.common.model.vo.OpenDashboardId;


public interface SendDashboardDataGateway {

    void execute(OpenDashboardId openDashboardId, OutgoingMessage outgoingMessage);

}
