package gnoolson.saturday.dashboard.model;

import gnoolson.saturday.dashboard.model.vo.OpenDashboardRequestId;
import lombok.Getter;

public abstract class OpenDashboardRequest {

    @Getter
    private final OpenDashboardRequestId id = new OpenDashboardRequestId();

    /*
     *
     *
     * */
    abstract public boolean setResult(Object data);

}
