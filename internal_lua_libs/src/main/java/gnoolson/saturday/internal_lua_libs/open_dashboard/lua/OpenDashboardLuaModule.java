package gnoolson.saturday.internal_lua_libs.open_dashboard.lua;


import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.internal_lua_libs.open_dashboard.Id;
import gnoolson.saturday.internal_lua_libs.open_dashboard.OpenDashboard;
import gnoolson.saturday.internal_lua_libs.open_dashboard.OpenDashboardFunctionality;
import gnoolson.saturday.lua_script_executor.lib.LuaModule;
import lombok.RequiredArgsConstructor;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import java.util.List;
import java.util.UUID;

public class OpenDashboardLuaModule extends LuaTable implements LuaModule {

    private OpenDashboardFunctionality openDashboardFunctionality;

    /*
     *
     *
     * */
    public OpenDashboardLuaModule() {
        set("byProjectId", new ByProjectIdFunction(this));
        set("byDashboardId", new ByDashboardIdFunction(this));
    }

    @Override
    public String getId() {
        return Id.VALUE;
    }

    @Override
    public void updateFunctionality(Object functionality) {
        this.openDashboardFunctionality = (OpenDashboardFunctionality) functionality;
    }

    @Override
    public void release() {

    }

    @Override
    public String name() {
        return "open_dashboard";
    }

    @Override
    public LuaTable getInstance() {
        return this;
    }

    /*
     *
     *
     * */
    @RequiredArgsConstructor
    public static class ByProjectIdFunction extends OneArgFunction {

        private final OpenDashboardLuaModule clientsLuaModule;

        @Override
        public LuaValue call(LuaValue projectIdLuaValue) {
            String projectIdStr = LuaArgUtils.getStringFromFunctionArgs(projectIdLuaValue, 1, "projectId");
            List<OpenDashboard> openDashboards = clientsLuaModule.openDashboardFunctionality.getOpenDashboards(ProjectId.of(UUID.fromString(projectIdStr)));

            LuaTable resultArray = new LuaTable();
            int index = 1;
            for (OpenDashboard openDashboard : openDashboards) {
                resultArray.set(index++, new OpenDashboardLuaTable(openDashboard));
            }

            return resultArray;
        }
    }

    @RequiredArgsConstructor
    public static class ByDashboardIdFunction extends OneArgFunction {

        private final OpenDashboardLuaModule clientsLuaModule;

        @Override
        public LuaValue call(LuaValue dashboardIdLuaValue) {
            String dashboardIdStr = LuaArgUtils.getStringFromFunctionArgs(dashboardIdLuaValue, 1, "dashboardId");

            DashboardId dashboardId = DashboardId.of(UUID.fromString(dashboardIdStr));

            List<OpenDashboard> openDashboards = clientsLuaModule.openDashboardFunctionality.getOpenDashboards(dashboardId);
            LuaTable resultArray = new LuaTable();
            int index = 1;

            for (OpenDashboard openDashboard : openDashboards) {
                if (dashboardId.equals(openDashboard.getOpenDashboardId().getDashboardId())) {
                    resultArray.set(index++, new OpenDashboardLuaTable(openDashboard));
                }
            }

            return resultArray;
        }
    }

}
