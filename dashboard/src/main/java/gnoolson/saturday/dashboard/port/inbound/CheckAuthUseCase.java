package gnoolson.saturday.dashboard.port.inbound;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.Role;


public interface CheckAuthUseCase {

    boolean execute(DashboardId id, Role role);

}
