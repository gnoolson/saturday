package gnoolson.saturday.dashboard.port.inbound;

import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.common.model.vo.Access;
import gnoolson.saturday.dashboard.model.vo.Html;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public interface CreateDashboardUseCase {

    DashboardId execute(DashboardDto dashboardDto);

    @Getter
    @RequiredArgsConstructor
    class DashboardDto {
        private final ProjectId projectId;
        private final DashboardName name;
        private final Html html;
        private final Description description;
        private final ScriptId scriptId;
        private final Access access;
    }

}
