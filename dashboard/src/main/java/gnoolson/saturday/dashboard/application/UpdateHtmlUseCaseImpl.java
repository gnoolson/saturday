package gnoolson.saturday.dashboard.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.dashboard.model.entity.Dashboard;
import gnoolson.saturday.dashboard.model.exception.DashboardNotFoundException;
import gnoolson.saturday.dashboard.model.vo.Html;
import gnoolson.saturday.dashboard.port.inbound.UpdateHtmlUseCase;
import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateHtmlUseCaseImpl implements UpdateHtmlUseCase {

    private final TransactionStarter transactionStarter;
    private final Locker locker;
    private final DashboardRepositoryGateway dashboardRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public void execute(DashboardId id, Html html) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(id))) {
            Dashboard dashboard = dashboardRepositoryGateway.find(id).orElseThrow(() -> new DashboardNotFoundException(id));
            dashboard.update(html);

            transactionStarter.doIt(() -> {
                dashboardRepositoryGateway.save(dashboard);
            });
        }
    }

}
