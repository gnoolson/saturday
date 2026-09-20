package gnoolson.saturday.dashboard.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.dashboard.model.exception.DashboardNotFoundException;
import gnoolson.saturday.dashboard.port.inbound.DeleteDashboardUseCase;
import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class DeleteDashboardUseCaseImpl implements DeleteDashboardUseCase {

    private final DashboardRepositoryGateway dashboardRepositoryGateway;
    private final TransactionStarter transactionStarter;
    private final Locker locker;

    /*
     *
     *
     * */
    @Override
    public boolean execute(DashboardId id) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(id))) {
            transactionStarter.doIt(() -> {
                dashboardRepositoryGateway.find(id).orElseThrow(() -> new DashboardNotFoundException(id));
                dashboardRepositoryGateway.delete(id);
            });

            return true;
        } catch (Exception e) {
            log.warn("Exception", e);
            return false;
        }
    }

}
