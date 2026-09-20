package gnoolson.saturday.dashboard.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.DashboardName;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.dashboard.model.entity.Dashboard;
import gnoolson.saturday.dashboard.model.exception.DashboardNotFoundException;
import gnoolson.saturday.dashboard.port.inbound.UpdateDashboardUseCase;
import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
import gnoolson.saturday.dashboard.port.outbound.ScriptIdCheckerGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateDashboardUseCaseImpl implements UpdateDashboardUseCase {

    private final TransactionStarter transactionStarter;
    private final Locker locker;
    private final DashboardRepositoryGateway dashboardRepositoryGateway;
    private final ScriptIdCheckerGateway scriptIdCheckerGateway;

    /*
     *
     *
     *  */
    @Override
    public void execute(DashboardDto dashboardDto) {
        Dashboard dashboard = dashboardRepositoryGateway.find(dashboardDto.getId()).orElseThrow(() -> new DashboardNotFoundException(dashboardDto.getId()));

        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(dashboardDto.getId()),
                LockId.of(dashboardDto.getScriptId()),
                LockId.of(dashboard.getName()),
                LockId.of(dashboardDto.getName()),
                LockId.of(dashboard.getScriptId()),
                LockId.of(dashboard.getProjectId()))) {

            checkName(dashboardDto.getProjectId(), dashboard.getName(), dashboardDto.getName());
            checkScript(dashboard.getProjectId(), dashboardDto.getScriptId());

            dashboard.update(dashboardDto.getName(), dashboardDto.getDescription(), dashboardDto.getScriptId(), dashboardDto.getAccess());

            transactionStarter.doIt(() -> {
                dashboardRepositoryGateway.save(dashboard);
            });
        }
    }

    /*
     *
     *
     * */
    private void checkScript(ProjectId projectId, ScriptId scriptId) {
        if (!scriptIdCheckerGateway.exists(projectId, scriptId))
            throw new RuntimeException(String.format("Script \"%s\" was not found", scriptId.getValue().toString())); // +
    }

    private void checkName(ProjectId projectId, DashboardName name, DashboardName newName) {
        if (name.equals(newName))
            return;

        if (dashboardRepositoryGateway.find(projectId, newName).isPresent())
            throw new RuntimeException(String.format("Dashboard \"%s\" already exists in Project \"%s\"", newName.getValue(), projectId.getValue())); // +
    }

}
