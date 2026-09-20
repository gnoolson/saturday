package gnoolson.saturday.dashboard.port.inbound;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.DashboardName;


public interface GetDashboardNameUseCase {

    DashboardName execute(DashboardId id);

}
