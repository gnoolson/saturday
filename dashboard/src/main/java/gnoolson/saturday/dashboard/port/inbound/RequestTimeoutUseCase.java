package gnoolson.saturday.dashboard.port.inbound;

import gnoolson.saturday.dashboard.model.vo.OpenDashboardRequestId;

public interface RequestTimeoutUseCase {

    void execute(OpenDashboardRequestId id);

}
