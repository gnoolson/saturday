package gnoolson.saturday.dashboard.port.inbound;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.dashboard.model.exception.NotAuthorized;
import gnoolson.saturday.dashboard.model.vo.Html;


public interface GetDashboardHtmlUseCase {

    Html execute(DashboardId id, Role role) throws NotAuthorized;

}
