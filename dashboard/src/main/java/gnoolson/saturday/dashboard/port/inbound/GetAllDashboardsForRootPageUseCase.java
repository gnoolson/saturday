package gnoolson.saturday.dashboard.port.inbound;

import gnoolson.saturday.common.model.vo.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

public interface GetAllDashboardsForRootPageUseCase {

    List<DashboardDto> execute(Role role);

    @Getter
    @RequiredArgsConstructor
    class DashboardDto {
        private final DashboardId id;
        private final ProjectId projectId;
        private final DashboardName name;
        private final Description description;
    }

}
