package gnoolson.saturday.dashboard.port.inbound;

import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.common.model.vo.Access;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


public interface GetAllDashboardsUseCase {

    List<DashboardDto> execute();

    @Getter
    @RequiredArgsConstructor
    class DashboardDto {
        private final DashboardId id;
        private final ProjectId projectId;
        private final DashboardName name;
        private final Description description;
        private final Access access;
    }

}
