package gnoolson.saturday.app.script;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.dashboard.port.inbound.GetOpenDashboardsUseCase;
import gnoolson.saturday.internal_lua_libs.open_dashboard.OpenDashboard;
import gnoolson.saturday.internal_lua_libs.open_dashboard.OpenDashboardsProviderGateway;
import gnoolson.saturday.internal_lua_libs.open_dashboard.SendDashboardDataGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class OpenDashboardProviderGatewayImpl implements OpenDashboardsProviderGateway {

    private final GetOpenDashboardsUseCase getOpenDashboardsUseCase;
    private final SendDashboardDataGateway sendDashboardDataGateway;

    /*
     *
     *
     * */
    @Override
    public List<OpenDashboard> execute(ProjectId id) {

        List<GetOpenDashboardsUseCase.OpenDashboardDto> openDashboardDtoList = getOpenDashboardsUseCase.execute(id);
        List<OpenDashboard> result = new ArrayList<>(openDashboardDtoList.size());

        for (GetOpenDashboardsUseCase.OpenDashboardDto openDashboardDto : openDashboardDtoList) {
            result.add(new OpenDashboard(
                    openDashboardDto.getId(),
                    openDashboardDto.getProjectId(),
                    openDashboardDto.getName(),
                    sendDashboardDataGateway
            ));
        }

        return result;
    }

    @Override
    public List<OpenDashboard> execute(DashboardId dashboardId) {
        List<GetOpenDashboardsUseCase.OpenDashboardDto> openDashboardDtoList = getOpenDashboardsUseCase.execute(dashboardId);
        List<OpenDashboard> result = new ArrayList<>(openDashboardDtoList.size());

        for (GetOpenDashboardsUseCase.OpenDashboardDto openDashboardDto : openDashboardDtoList) {
            result.add(new OpenDashboard(
                    openDashboardDto.getId(),
                    openDashboardDto.getProjectId(),
                    openDashboardDto.getName(),
                    sendDashboardDataGateway
            ));
        }

        return result;
    }

}
