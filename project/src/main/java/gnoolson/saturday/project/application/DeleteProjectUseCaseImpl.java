package gnoolson.saturday.project.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.eventbus.events.ProjectDeletedEvent;
import gnoolson.saturday.common.eventbus.local.LocalEventBus;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.project.model.exception.ProjectNotFoundException;
import gnoolson.saturday.project.port.inbound.DeleteProjectUseCase;
import gnoolson.saturday.project.port.outbound.ProjectRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class DeleteProjectUseCaseImpl implements DeleteProjectUseCase {

    private final ProjectRepositoryGateway projectRepositoryGateway;
    private final TransactionStarter transactionStarter;
    private final LocalEventBus localEventBus;
    private final Locker locker;

    /*
     *
     *
     * */
    @Override
    public boolean execute(ProjectId id) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(id))) {
            transactionStarter.doIt(() -> {
                transactionStarter.doAfterTransactionCommit(() -> {
                    localEventBus.emit(new ProjectDeletedEvent(id));
                });

                projectRepositoryGateway.find(id).orElseThrow(() -> new ProjectNotFoundException(id));
                projectRepositoryGateway.delete(id);
            });

            return true;
        } catch (Exception e) {
            log.warn("Exception", e);
            return false;
        }
    }

}
