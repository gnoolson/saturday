package gnoolson.saturday.subscription.port.outbound;

import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.SubscriptionId;
import gnoolson.saturday.common.model.vo.TopicFilter;
import gnoolson.saturday.subscription.model.entity.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepositoryGateway {

    List<Subscription> findByClientId(ClientId clientId);

    Optional<Subscription> find(SubscriptionId subscriptionId);

    SubscriptionId save(Subscription subscription);

    void delete(SubscriptionId subscriptionId);

    boolean isTopicFilterExist(ClientId clientId, TopicFilter topicFilter);

    List<Subscription> findAll();

}
