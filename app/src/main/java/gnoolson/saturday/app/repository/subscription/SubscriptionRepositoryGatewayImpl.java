package gnoolson.saturday.app.repository.subscription;

import gnoolson.saturday.app.repository.subscription.entity.SubscriptionEntity;
import gnoolson.saturday.app.repository.subscription.entity.SubscriptionMapper;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.SubscriptionId;
import gnoolson.saturday.common.model.vo.TopicFilter;
import gnoolson.saturday.subscription.model.entity.Subscription;
import gnoolson.saturday.subscription.port.outbound.SubscriptionRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class SubscriptionRepositoryGatewayImpl implements SubscriptionRepositoryGateway {

    private final SubscriptionJPARepository subscriptionJPARepository;

    /*
     *
     *
     * */
    @Override
    public List<Subscription> findByClientId(ClientId clientId) {
        List<SubscriptionEntity> subscriptions = subscriptionJPARepository.findByClientId(clientId.getValue());
        return SubscriptionMapper.toDomain(subscriptions);
    }


    @Override
    public Optional<Subscription> find(SubscriptionId subscriptionId) {
        Optional<SubscriptionEntity> subscriptionEntityOpt = subscriptionJPARepository.findById(subscriptionId.getValue());
        return subscriptionEntityOpt.map(SubscriptionMapper::toDomain);
    }


    @Override
    public boolean isTopicFilterExist(ClientId clientId, TopicFilter topicFilter) {
        Optional<SubscriptionEntity> subscriptionEntityOpt = subscriptionJPARepository.find(clientId.getValue(), topicFilter.getValue());
        return subscriptionEntityOpt.isPresent();
    }


    @Override
    public List<Subscription> findAll() {
        Iterable<SubscriptionEntity> subscriptions = subscriptionJPARepository.findAll();
        return SubscriptionMapper.toDomain(subscriptions);
    }


    @Override
    public SubscriptionId save(Subscription subscription) {
        SubscriptionEntity subscriptionEntity = SubscriptionMapper.toJPA(subscription);
        subscriptionJPARepository.save(subscriptionEntity);
        return SubscriptionId.of(subscriptionEntity.getId());
    }


    @Override
    public void delete(SubscriptionId subscriptionId) {
        subscriptionJPARepository.delete(new SubscriptionEntity(subscriptionId.getValue()));
    }

}
