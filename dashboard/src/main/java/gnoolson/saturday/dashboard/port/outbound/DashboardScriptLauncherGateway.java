package gnoolson.saturday.dashboard.port.outbound;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.OpenDashboardId;
import gnoolson.saturday.common.model.vo.ScriptId;

import java.util.Map;


public interface DashboardScriptLauncherGateway {

    void execute(
            ScriptId scriptId,
            DashboardId dashboardId,
            OpenDashboardId openDashboardId,
            Map<String, Object> request);

}
