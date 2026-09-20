package gnoolson.saturday.subscription.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.eventbus.events.ClientSubscriptionUpdated;
import gnoolson.saturday.common.eventbus.local.LocalEventBus;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.SubscriptionId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.subscription.model.entity.Subscription;
import gnoolson.saturday.subscription.model.exception.SubscriptionNotFoundException;
import gnoolson.saturday.subscription.port.inbound.DeleteSubscriptionUseCase;
import gnoolson.saturday.subscription.port.outbound.SubscriptionRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class DeleteSubscriptionUseCaseImpl implements DeleteSubscriptionUseCase {

    private final TransactionStarter transactionStarter;
    private final Locker locker;
    private final SubscriptionRepositoryGateway subscriptionRepositoryGateway;
    private final LocalEventBus localEventBus;

    /*
     *
     *
     * */
    @Override
    public boolean execute(ClientId clientId, SubscriptionId subscriptionId) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(clientId), LockId.of(subscriptionId))) {
            transactionStarter.doIt(() -> {
                Subscription subscription = subscriptionRepositoryGateway.find(subscriptionId).orElseThrow(() -> new SubscriptionNotFoundException(subscriptionId));
                if (!subscription.getClientId().equals(clientId))
                    throw new RuntimeException(String.format("Subscription \"%s\" was not found for Client \"%s\"", subscriptionId.getValue(), clientId.getValue())); // +

                subscriptionRepositoryGateway.delete(subscriptionId);

                transactionStarter.doAfterTransactionCommit(() -> {
                    localEventBus.emit(new ClientSubscriptionUpdated(subscription.getProjectId(), clientId));
                });
            });
            return true;
        } catch (Exception e) {
            log.warn("Exception", e);
            return false;
        }
    }

}
