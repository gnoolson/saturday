package gnoolson.saturday.internal_lua_libs.open_dashboard;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.ProjectId;

import java.util.List;

public interface OpenDashboardsProviderGateway {

    List<OpenDashboard> execute(ProjectId id);

    List<OpenDashboard> execute(DashboardId dashboardId);

}
