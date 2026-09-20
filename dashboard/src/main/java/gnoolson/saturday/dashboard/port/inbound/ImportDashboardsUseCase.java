package gnoolson.saturday.dashboard.port.inbound;

import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.common.model.vo.Access;
import gnoolson.saturday.dashboard.model.vo.Html;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


public interface ImportDashboardsUseCase {

    void execute(List<DashboardDto> dashboards);

    @Getter
    @RequiredArgsConstructor
    class DashboardDto {
        private final DashboardId id;
        private final ProjectId projectId;
        private final DashboardName name;
        private final Description description;
        private final Html html;
        private final ScriptId scriptId;
        private final Access access;
    }

}
