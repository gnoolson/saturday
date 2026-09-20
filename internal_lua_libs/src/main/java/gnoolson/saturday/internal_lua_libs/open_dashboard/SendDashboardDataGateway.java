package gnoolson.saturday.internal_lua_libs.open_dashboard;

import gnoolson.saturday.common.model.vo.OpenDashboardId;

public interface SendDashboardDataGateway {

    boolean execute(OpenDashboardId openDashboardId, OutgoingMessage outgoingMessage);

}
