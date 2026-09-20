package gnoolson.saturday.dashboard.port.inbound;

import gnoolson.saturday.common.model.vo.OpenDashboardId;
import gnoolson.saturday.dashboard.model.OpenDashboardRequest;

public interface RegistrationOpenDashboardUseCase {

    void execute(OpenDashboardId openDashboardId, OpenDashboardRequest openDashboardRequest);

}
