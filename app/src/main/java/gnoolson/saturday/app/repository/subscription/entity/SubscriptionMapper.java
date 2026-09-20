package gnoolson.saturday.app.repository.subscription.entity;

import gnoolson.saturday.app.repository.client.entity.ClientEntity;
import gnoolson.saturday.app.repository.project.entity.ProjectEntity;
import gnoolson.saturday.app.repository.script.entity.ScriptEntity;
import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.subscription.model.entity.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SubscriptionMapper {

    public static List<Subscription> toDomain(List<SubscriptionEntity> subscriptionEntityList) {
        return subscriptionEntityList.stream().map(SubscriptionMapper::toDomain).collect(Collectors.toList());
    }

    public static Subscription toDomain(SubscriptionEntity subscriptionEntity) {
        return new Subscription(
                SubscriptionId.of(subscriptionEntity.getId()),
                ProjectId.of(subscriptionEntity.getProject().getId()),
                ClientId.of(subscriptionEntity.getClient().getId()),
                ScriptId.of(subscriptionEntity.getScript().getId()),
                TopicFilter.of(subscriptionEntity.getTopicFilter()),
                Description.of(subscriptionEntity.getDescription())
        );
    }

    public static SubscriptionEntity toJPA(Subscription subscription) {
        return new SubscriptionEntity(
                subscription.getId().isEmpty() ? UUID.randomUUID() : subscription.getId().getValue(),
                new ProjectEntity(subscription.getProjectId().getValue()),
                new ScriptEntity(subscription.getScriptId().getValue()),
                new ClientEntity(subscription.getClientId().getValue()),
                subscription.getDescription().getValue(),
                subscription.getTopicFilter().getValue()
        );
    }

    public static List<Subscription> toDomain(Iterable<SubscriptionEntity> subscriptions) {
        List<Subscription> result = new ArrayList<>();

        for (SubscriptionEntity subscription : subscriptions) {
            result.add(toDomain(subscription));
        }

        return result;
    }

}
