package gnoolson.saturday.internal_lua_libs.open_dashboard;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.lua_script_executor.Output;

import java.util.List;

public class OpenDashboardFunctionalityImpl implements OpenDashboardFunctionality {

    private OpenDashboardsProviderGateway openDashboardsProviderGateway;

    /*
     *
     *
     * */
    @Override
    public List<OpenDashboard> getOpenDashboards(ProjectId projectId) {
        return openDashboardsProviderGateway.execute(projectId);
    }

    @Override
    public List<OpenDashboard> getOpenDashboards(DashboardId dashboardId) {
        return openDashboardsProviderGateway.execute(dashboardId);
    }

    @Override
    public void setup(OpenDashboardsProviderGateway openDashboardsProviderGateway) {
        this.openDashboardsProviderGateway = openDashboardsProviderGateway;
    }

    @Override
    public String getLuaLibId() {
        return Id.VALUE;
    }

    @Override
    public void writeResultToOutput(Output output) {

    }

    @Override
    public Object getFunctionalityInstance() {
        return this;
    }

}
