package gnoolson.saturday.subscription.application;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.subscription.model.entity.Subscription;
import gnoolson.saturday.subscription.port.inbound.GetSubscriptionsForExportUseCase;
import gnoolson.saturday.subscription.port.outbound.SubscriptionRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetSubscriptionsForExportUseCaseImpl implements GetSubscriptionsForExportUseCase {

    private final SubscriptionRepositoryGateway subscriptionRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public List<SubscriptionDto> execute(Set<ProjectId> projectIdSet) {
        List<Subscription> subscriptions = subscriptionRepositoryGateway.findAll();

        return subscriptions.stream()
                .filter(subscription -> {
                    return projectIdSet.contains(subscription.getProjectId());
                })
                .map(
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
