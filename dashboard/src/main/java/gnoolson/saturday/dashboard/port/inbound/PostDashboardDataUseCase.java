package gnoolson.saturday.dashboard.port.inbound;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.OpenDashboardId;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.dashboard.model.exception.NotAuthorized;

import java.util.Map;

public interface PostDashboardDataUseCase {

    void execute(DashboardId id, OpenDashboardId openDashboardId, Map<String, Object> requestData, Role role) throws NotAuthorized;

}
