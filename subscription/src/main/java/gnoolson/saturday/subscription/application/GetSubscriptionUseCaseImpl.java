package gnoolson.saturday.subscription.application;

import gnoolson.saturday.common.model.vo.SubscriptionId;
import gnoolson.saturday.subscription.model.entity.Subscription;
import gnoolson.saturday.subscription.model.exception.SubscriptionNotFoundException;
import gnoolson.saturday.subscription.port.inbound.GetSubscriptionUseCase;
import gnoolson.saturday.subscription.port.outbound.SubscriptionRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class GetSubscriptionUseCaseImpl implements GetSubscriptionUseCase {

    private final SubscriptionRepositoryGateway subscriptionRepositoryGateway;

    @Override
    public SubscriptionDto execute(SubscriptionId subscriptionId) {
        Subscription subscription = subscriptionRepositoryGateway.find(subscriptionId).orElseThrow(() -> new SubscriptionNotFoundException(subscriptionId));

        return new SubscriptionDto(
                subscription.getId(),
                subscription.getClientId(),
                subscription.getScriptId(),
                subscription.getDescription(),
                subscription.getTopicFilter(),
                subscription.getProjectId()
        );
    }

}
