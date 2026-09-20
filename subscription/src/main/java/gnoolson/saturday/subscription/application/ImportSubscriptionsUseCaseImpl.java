package gnoolson.saturday.subscription.application;

import gnoolson.saturday.common.eventbus.events.ClientSubscriptionUpdated;
import gnoolson.saturday.common.eventbus.local.LocalEventBus;
import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.subscription.model.entity.Subscription;
import gnoolson.saturday.subscription.port.inbound.ImportSubscriptionsUseCase;
import gnoolson.saturday.subscription.port.outbound.GetClientProjectIdGateway;
import gnoolson.saturday.subscription.port.outbound.ScriptIdCheckerGateway;
import gnoolson.saturday.subscription.port.outbound.SubscriptionRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ImportSubscriptionsUseCaseImpl implements ImportSubscriptionsUseCase {

    private final ScriptIdCheckerGateway scriptIdCheckerGateway;
    private final SubscriptionRepositoryGateway subscriptionRepositoryGateway;
    private final TransactionStarter transactionStarter;
    private final LocalEventBus localEventBus;
    private final GetClientProjectIdGateway getClientProjectIdGateway;

    /*
     *
     *
     * */
    @Override
    public void execute(List<SubscriptionDto> subscriptions) {
        for (SubscriptionDto subscription : subscriptions) {
            save(subscription);
        }
    }

    /*
     *
     *
     * */
    private void save(SubscriptionDto subscriptionDto) {
        ProjectId projectId = getClientProjectIdGateway.execute(subscriptionDto.getClientId());

        checkId(subscriptionDto.getId());
        checkScript(projectId, subscriptionDto.getScriptId());
        checkTopicFilter(subscriptionDto.getClientId(), subscriptionDto.getTopicFilter());

        Subscription subscription = new Subscription(
                subscriptionDto.getId(),
                projectId,
                subscriptionDto.getClientId(),
                subscriptionDto.getScriptId(),
                subscriptionDto.getTopicFilter(),
                subscriptionDto.getDescription()
        );

        subscriptionRepositoryGateway.save(subscription);
        transactionStarter.doAfterTransactionCommit(() -> {
            localEventBus.emit(new ClientSubscriptionUpdated(projectId, subscriptionDto.getClientId()));
        });
    }

    private void checkTopicFilter(ClientId clientId, TopicFilter topicFilter) {
        if (subscriptionRepositoryGateway.isTopicFilterExist(clientId, topicFilter))
            throw new RuntimeException(String.format("TopicFilter \"%s\" already exists for Client \"%s\"", topicFilter.getValue(), clientId.getValue())); // +
    }

    private void checkScript(ProjectId projectId, ScriptId scriptId) {
        if (!scriptIdCheckerGateway.exists(projectId, scriptId))
            throw new RuntimeException(String.format("Script \"%s\" was not found in Project \"%s\"", scriptId.getValue().toString(), projectId.getValue())); // +
    }

    private void checkId(SubscriptionId id) {
        if (idExists(id))
            throw new RuntimeException(String.format("Subscription \"%s\" already exists", id.getValue().toString())); // +
    }

    private boolean idExists(SubscriptionId id) {
        return subscriptionRepositoryGateway.find(id).isPresent();
    }

}
