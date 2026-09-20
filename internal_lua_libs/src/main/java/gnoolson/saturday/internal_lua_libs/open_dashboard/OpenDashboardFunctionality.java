package gnoolson.saturday.internal_lua_libs.open_dashboard;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.lua_script_executor.lib.Functionality;

import java.util.List;

public interface OpenDashboardFunctionality extends Functionality {

    List<OpenDashboard> getOpenDashboards(ProjectId id);

    List<OpenDashboard> getOpenDashboards(DashboardId dashboardId);

    void setup(OpenDashboardsProviderGateway openDashboardsProviderGateway);

}
