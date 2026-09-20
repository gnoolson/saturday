package gnoolson.saturday.dashboard.application;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.OpenDashboardId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.dashboard.model.OpenDashboardRequest;
import gnoolson.saturday.dashboard.model.OpenDashboardSession;
import gnoolson.saturday.dashboard.model.vo.DashboardResponse;
import gnoolson.saturday.dashboard.model.vo.OpenDashboardRequestId;

import java.util.List;

public interface OpenDashboardsService {

    boolean send(OpenDashboardId openDashboardId, DashboardResponse response);

    List<OpenDashboardSession> getOpenDashboardSessionsInProject(ProjectId id);

    void registration(OpenDashboardId openDashboardId, OpenDashboardRequest openDashboardRequest);

    void timeout(OpenDashboardRequestId id);

    List<OpenDashboardSession> getOpenDashboardSessions(DashboardId id);

}
