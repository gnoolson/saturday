package gnoolson.saturday.subscription.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.eventbus.events.ClientSubscriptionUpdated;
import gnoolson.saturday.common.eventbus.local.LocalEventBus;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.TopicFilter;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.subscription.model.entity.Subscription;
import gnoolson.saturday.subscription.model.exception.SubscriptionNotFoundException;
import gnoolson.saturday.subscription.port.inbound.UpdateSubscriptionUseCase;
import gnoolson.saturday.subscription.port.outbound.SubscriptionRepositoryGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateSubscriptionUseCaseImpl implements UpdateSubscriptionUseCase {

    private final SubscriptionRepositoryGateway subscriptionRepositoryGateway;
    private final LocalEventBus localEventBus;
    private final TransactionStarter transactionStarter;
    private final Locker locker;

    /*
     *
     *
     * */
    @Override
    public void execute(SubscriptionDto subscriptionDto) {
        Subscription subscription = subscriptionRepositoryGateway.find(subscriptionDto.getId()).orElseThrow(() -> new SubscriptionNotFoundException(subscriptionDto.getId()));

        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(subscriptionDto.getId()), LockId.of(subscriptionDto.getClientId()))) {
            checkTopic(subscriptionDto.getClientId(), subscription.getTopicFilter(), subscriptionDto.getTopicFilter());

            subscription.update(subscriptionDto.getTopicFilter(), subscriptionDto.getDescription());

            transactionStarter.doIt(() -> {
                subscriptionRepositoryGateway.save(subscription);
                transactionStarter.doAfterTransactionCommit(() -> {
                    localEventBus.emit(new ClientSubscriptionUpdated(subscription.getProjectId(), subscription.getClientId()));
                });
            });
        }
    }

    /*
     *
     *
     * */
    private void checkTopic(ClientId clientId, TopicFilter oldTopicFilter, TopicFilter newTopicFilter) {
        if (oldTopicFilter.equals(newTopicFilter))
            return;

        if (subscriptionRepositoryGateway.isTopicFilterExist(clientId, oldTopicFilter))
            throw new RuntimeException(String.format("TopicFilter \"%s\" already exists for Client \"%s\"", newTopicFilter.getValue(), clientId.getValue().toString())); // +
    }

}
