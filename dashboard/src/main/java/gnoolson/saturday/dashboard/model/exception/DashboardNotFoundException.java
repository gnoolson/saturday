package gnoolson.saturday.dashboard.model.exception;

import gnoolson.saturday.common.model.vo.DashboardId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DashboardNotFoundException extends RuntimeException {

    private final DashboardId dashboardId;

    @Override
    public String getMessage() {
        return String.format("Dashboard \"%s\" was not found", dashboardId.getValue().toString());
    }

}
