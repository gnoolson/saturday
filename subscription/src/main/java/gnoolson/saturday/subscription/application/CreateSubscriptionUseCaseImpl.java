package gnoolson.saturday.subscription.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.eventbus.events.ClientSubscriptionUpdated;
import gnoolson.saturday.common.eventbus.local.LocalEventBus;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.subscription.model.entity.Subscription;
import gnoolson.saturday.subscription.port.inbound.CreateSubscriptionUseCase;
import gnoolson.saturday.subscription.port.outbound.GetClientProjectIdGateway;
import gnoolson.saturday.subscription.port.outbound.ScriptIdCheckerGateway;
import gnoolson.saturday.subscription.port.outbound.SubscriptionRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class CreateSubscriptionUseCaseImpl implements CreateSubscriptionUseCase {

    private final Locker locker;
    private final TransactionStarter transactionStarter;
    private final ScriptIdCheckerGateway scriptIdCheckerGateway;
    private final SubscriptionRepositoryGateway subscriptionRepositoryGateway;
    private final LocalEventBus localEventBus;
    private final GetClientProjectIdGateway getClientProjectIdGateway;

    /*
     *
     *
     * */
    @Override
    public SubscriptionId execute(SubscriptionDto subscriptionDto) {
        ProjectId projectId = getClientProjectIdGateway.execute(subscriptionDto.getClientId());

        try (Locker.LockHandle ignore = locker.lockIds(
                LockId.of(subscriptionDto.getClientId()),
                LockId.of(subscriptionDto.getScriptId()),
                LockId.of(projectId))) {

            checkScript(projectId, subscriptionDto.getScriptId());
            checkTopic(subscriptionDto.getClientId(), subscriptionDto.getTopicFilter());

            Subscription subscription = new Subscription(
                    SubscriptionId.empty(),
                    projectId,
                    subscriptionDto.getClientId(),
                    subscriptionDto.getScriptId(),
                    subscriptionDto.getTopicFilter(),
                    subscriptionDto.getDescription()
            );

            AtomicReference<SubscriptionId> result = new AtomicReference<>();

            transactionStarter.doIt(() -> {
                SubscriptionId id = subscriptionRepositoryGateway.save(subscription);
                result.set(id);
                transactionStarter.doAfterTransactionCommit(() -> {
                    localEventBus.emit(new ClientSubscriptionUpdated(projectId, subscriptionDto.getClientId()));
                });
            });

            return result.get();
        }

    }

    /*
     *
     *
     * */
    private void checkTopic(ClientId clientId, TopicFilter topicFilter) {
        if (subscriptionRepositoryGateway.isTopicFilterExist(clientId, topicFilter))
            throw new RuntimeException(String.format("TopicFilter \"%s\" already exists for Client \"%s\"", topicFilter.getValue(), clientId.getValue())); // +
    }

    private void checkScript(ProjectId projectId, ScriptId scriptId) {
        if (!scriptIdCheckerGateway.exists(projectId, scriptId))
            throw new RuntimeException(String.format("Script \"%s\" was not found in Project \"%s\"", scriptId.getValue().toString(), projectId.getValue())); // +
    }

}
