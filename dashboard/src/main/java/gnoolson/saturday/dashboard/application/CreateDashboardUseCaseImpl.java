package gnoolson.saturday.dashboard.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.DashboardName;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.dashboard.model.entity.Dashboard;
import gnoolson.saturday.dashboard.port.inbound.CreateDashboardUseCase;
import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
import gnoolson.saturday.dashboard.port.outbound.ProjectIdCheckerGateway;
import gnoolson.saturday.dashboard.port.outbound.ScriptIdCheckerGateway;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class CreateDashboardUseCaseImpl implements CreateDashboardUseCase {

    private final DashboardRepositoryGateway dashboardRepositoryGateway;
    private final TransactionStarter transactionStarter;
    private final Locker locker;
    private final ScriptIdCheckerGateway scriptIdCheckerGateway;
    private final ProjectIdCheckerGateway projectIdCheckerGateway;

    /*
     *
     *
     * */
    @Override
    public DashboardId execute(DashboardDto dashboardDto) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(dashboardDto.getName()),
                LockId.of(dashboardDto.getScriptId()),
                LockId.of(dashboardDto.getProjectId()))) {

            checkProject(dashboardDto.getProjectId());
            checkName(dashboardDto.getProjectId(), dashboardDto.getName());
            checkScript(dashboardDto.getProjectId(), dashboardDto.getScriptId());

            Dashboard dashboard = new Dashboard(
                    DashboardId.empty(),
                    dashboardDto.getProjectId(),
                    dashboardDto.getName(),
                    dashboardDto.getDescription(),
                    dashboardDto.getHtml(),
                    dashboardDto.getScriptId(),
                    dashboardDto.getAccess()
            );

            AtomicReference<DashboardId> result = new AtomicReference<>();
            transactionStarter.doIt(() -> {
                DashboardId id = dashboardRepositoryGateway.save(dashboard);
                result.set(id);
            });

            return result.get();
        }
    }

    /*
     *
     *
     * */
    private void checkProject(ProjectId projectId) {
        if (!projectIdCheckerGateway.exists(projectId))
            throw new RuntimeException(String.format("Project \"%s\" was not found", projectId.getValue().toString())); // +
    }

    private void checkScript(ProjectId projectId, ScriptId scriptId) {
        if (!scriptIdCheckerGateway.exists(projectId, scriptId))
            throw new RuntimeException(String.format("Script \"%s\" was not found", scriptId.getValue().toString())); // +
    }

    private void checkName(ProjectId projectId, DashboardName name) {
        if (dashboardRepositoryGateway.find(projectId, name).isPresent())
            throw new RuntimeException(String.format("Dashboard \"%s\" already exists in Project \"%s\"", name.getValue(), projectId.getValue())); // +
    }

}
