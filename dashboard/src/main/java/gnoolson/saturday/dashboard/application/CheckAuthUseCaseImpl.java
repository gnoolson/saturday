package gnoolson.saturday.dashboard.application;

import gnoolson.saturday.common.model.vo.Access;
import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.dashboard.model.entity.Dashboard;
import gnoolson.saturday.dashboard.model.exception.DashboardNotFoundException;
import gnoolson.saturday.dashboard.port.inbound.CheckAuthUseCase;
import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CheckAuthUseCaseImpl implements CheckAuthUseCase {

    private final DashboardRepositoryGateway dashboardRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public boolean execute(DashboardId id, Role role) {
        if (role == Role.EDITOR)
            return true;

        Optional<Dashboard> dashboardOptional = dashboardRepositoryGateway.find(id);
        Dashboard dashboard = dashboardOptional.orElseThrow(() -> new DashboardNotFoundException(id));

        if (role == Role.VIEWER && dashboard.getAccess() == Access.VIEWER)
            return true;

        if (dashboard.getAccess() == Access.ANONYMOUS)
            return true;

        return false;
    }


}
