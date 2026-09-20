package gnoolson.saturday.dashboard.application;

import gnoolson.saturday.dashboard.model.vo.OpenDashboardRequestId;
import gnoolson.saturday.dashboard.port.inbound.RequestTimeoutUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestTimeoutUseCaseImpl implements RequestTimeoutUseCase {

    private final OpenDashboardsService openDashboardsService;

    /*
     *
     *
     * */
    @Override
    public void execute(OpenDashboardRequestId id) {
        openDashboardsService.timeout(id);
    }

}
