package gnoolson.saturday.dashboard.port.inbound;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.DashboardName;
import gnoolson.saturday.common.model.vo.OpenDashboardId;
import gnoolson.saturday.common.model.vo.ProjectId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

public interface GetOpenDashboardsUseCase {

    List<OpenDashboardDto> execute(ProjectId id);

    List<OpenDashboardDto> execute(DashboardId id);

    /*
     *
     *
     * */
    @Getter
    @RequiredArgsConstructor
    class OpenDashboardDto {
        private final OpenDashboardId id;
        private final ProjectId projectId;
        private final DashboardName name;
    }

}
