package gnoolson.saturday.subscription.port.inbound;

import gnoolson.saturday.common.model.vo.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public interface UpdateSubscriptionUseCase {

    void execute(SubscriptionDto subscriptionDto);

    /*
     *
     *
     * */
    @Getter
    @RequiredArgsConstructor
    class SubscriptionDto {
        private final SubscriptionId id;
        private final ClientId clientId;
        private final Description description;
        private final TopicFilter topicFilter;
        private final QoS qos;
    }

}
