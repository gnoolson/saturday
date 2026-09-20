package gnoolson.saturday.app.web.subscription.dto;

import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.subscription.port.inbound.CreateSubscriptionUseCase;
import gnoolson.saturday.subscription.port.inbound.GetAllSubscriptionsUseCase;
import gnoolson.saturday.subscription.port.inbound.UpdateSubscriptionUseCase;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static List<SubscriptionDto> toDto(List<GetAllSubscriptionsUseCase.SubscriptionDto> subscriptionDtoList) {
        return subscriptionDtoList.stream().map(gnoolson.saturday.app.web.subscription.dto.SubscriptionDto::new).collect(Collectors.toList());
    }

    public static UpdateSubscriptionUseCase.SubscriptionDto toDomainDtoForUpdate(SubscriptionDto subscriptionDto) {
        return new UpdateSubscriptionUseCase.SubscriptionDto(
                SubscriptionId.of(subscriptionDto.getId()),
                ClientId.of(subscriptionDto.getClientId()),
                Description.of(subscriptionDto.getDescription()),
                TopicFilter.of(subscriptionDto.getTopicFilter())
        );
    }

    public static CreateSubscriptionUseCase.SubscriptionDto toDomainDtoForCreate(SubscriptionDto subscriptionDto) {
        return new CreateSubscriptionUseCase.SubscriptionDto(
                ClientId.of(subscriptionDto.getClientId()),
                ScriptId.of(subscriptionDto.getScriptId()),
                Description.of(subscriptionDto.getDescription()),
                TopicFilter.of(subscriptionDto.getTopicFilter())
        );
    }

}
