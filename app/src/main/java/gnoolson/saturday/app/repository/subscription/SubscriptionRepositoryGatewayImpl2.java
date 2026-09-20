package gnoolson.saturday.app.repository.subscription;

import gnoolson.saturday.app.repository.subscription.entity.SubscriptionEntity;
import gnoolson.saturday.client.model.vo.Subscription;
import gnoolson.saturday.client.port.outbound.SubscriptionRepositoryGateway;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.SubscriptionId;
import gnoolson.saturday.common.model.vo.TopicFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class SubscriptionRepositoryGatewayImpl2 implements SubscriptionRepositoryGateway {

    private final SubscriptionJPARepository subscriptionJPARepository;

    /*
     *
     *
     * */
    @Override
    public List<Subscription> findByClientId(ClientId clientId) {
        List<SubscriptionEntity> subscriptionEntities = subscriptionJPARepository.findByClientId(clientId.getValue());

        return subscriptionEntities.stream().map((subscriptionEntity -> {
            return new Subscription(SubscriptionId.of(subscriptionEntity.getId()), TopicFilter.of(subscriptionEntity.getTopicFilter()), ScriptId.of(subscriptionEntity.getScript().getId()));
        })).collect(Collectors.toList());
    }

}
