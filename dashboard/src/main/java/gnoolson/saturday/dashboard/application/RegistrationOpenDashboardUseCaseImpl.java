package gnoolson.saturday.dashboard.application;

import gnoolson.saturday.common.model.vo.OpenDashboardId;
import gnoolson.saturday.dashboard.model.OpenDashboardRequest;
import gnoolson.saturday.dashboard.port.inbound.RegistrationOpenDashboardUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegistrationOpenDashboardUseCaseImpl implements RegistrationOpenDashboardUseCase {

    private final OpenDashboardsService openDashboardsService;

    /*
     *
     *
     * */
    @Override
    public void execute(OpenDashboardId openDashboardId, OpenDashboardRequest openDashboardRequest) {
        openDashboardsService.registration(openDashboardId, openDashboardRequest);
    }

}
