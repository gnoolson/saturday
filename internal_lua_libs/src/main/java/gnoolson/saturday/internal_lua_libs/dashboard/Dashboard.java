package gnoolson.saturday.internal_lua_libs.dashboard;

import gnoolson.saturday.common.model.vo.OpenDashboardId;
import gnoolson.saturday.lua_script_executor.lib.Functionality;

import java.util.Optional;

public interface Dashboard extends Functionality {

    boolean isMessageAvailable();

    Optional<IncomingMessage> getMessage();

    void sendMessage(OutgoingMessage outgoingMessage);

    Optional<OpenDashboardId> getOpenDashboardId();

    void setup(SendDashboardDataGateway sendDashboardDataGateway);

    void setup(SendDashboardDataGateway sendDashboardDataGateway, OpenDashboardId openDashboardId, IncomingMessage incomingMessage);

}
