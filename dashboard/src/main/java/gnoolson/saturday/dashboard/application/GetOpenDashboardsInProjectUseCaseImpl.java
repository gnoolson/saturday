package gnoolson.saturday.dashboard.application;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.dashboard.model.OpenDashboardSession;
import gnoolson.saturday.dashboard.port.inbound.GetOpenDashboardsUseCase;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class GetOpenDashboardsInProjectUseCaseImpl implements GetOpenDashboardsUseCase {

    private final OpenDashboardsService openDashboardsService;

    /*
     *
     *
     * */
    @Override
    public List<OpenDashboardDto> execute(ProjectId id) {
        List<OpenDashboardSession> openDashboardSessionInProject = openDashboardsService.getOpenDashboardSessionsInProject(id);

        List<OpenDashboardDto> result = new ArrayList<>();
        for (OpenDashboardSession openDashboardSession : openDashboardSessionInProject) {
            result.add(new OpenDashboardDto(openDashboardSession.getOpenDashboardId(), openDashboardSession.getProjectId(), openDashboardSession.getDashboardName()));
        }

        return result;
    }

    @Override
    public List<OpenDashboardDto> execute(DashboardId id) {
        List<OpenDashboardSession> openDashboardSessionInProject = openDashboardsService.getOpenDashboardSessions(id);

        List<OpenDashboardDto> result = new ArrayList<>();
        for (OpenDashboardSession openDashboardSession : openDashboardSessionInProject) {
            result.add(new OpenDashboardDto(openDashboardSession.getOpenDashboardId(), openDashboardSession.getProjectId(), openDashboardSession.getDashboardName()));
        }

        return result;
    }

}
