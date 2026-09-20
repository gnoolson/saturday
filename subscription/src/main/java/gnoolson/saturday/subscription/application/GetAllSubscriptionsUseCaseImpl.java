package gnoolson.saturday.subscription.application;

import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.subscription.model.entity.Subscription;
import gnoolson.saturday.subscription.port.inbound.GetAllSubscriptionsUseCase;
import gnoolson.saturday.subscription.port.outbound.SubscriptionRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
public class GetAllSubscriptionsUseCaseImpl implements GetAllSubscriptionsUseCase {

    private final SubscriptionRepositoryGateway subscriptionRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public List<SubscriptionDto> execute(ClientId clientId) {
        List<Subscription> subscriptions = subscriptionRepositoryGateway.findByClientId(clientId);

        return subscriptions.stream().map(
                subscription -> {
                    return new SubscriptionDto(
                            subscription.getId(),
                            subscription.getClientId(),
                            subscription.getScriptId(),
                            subscription.getDescription(),
                            subscription.getTopicFilter(),
                            subscription.getProjectId()
                    );
                }
        ).collect(Collectors.toList());
    }

}
