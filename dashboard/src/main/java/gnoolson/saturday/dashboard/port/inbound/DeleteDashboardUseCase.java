package gnoolson.saturday.dashboard.port.inbound;

import gnoolson.saturday.common.model.vo.DashboardId;


public interface DeleteDashboardUseCase {

    boolean execute(DashboardId id);

}
