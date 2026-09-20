package gnoolson.saturday.subscription.port.inbound;

import gnoolson.saturday.common.model.vo.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public interface CreateSubscriptionUseCase {

    SubscriptionId execute(SubscriptionDto subscriptionDto);

    /*
     *
     *
     * */
    @Getter
    @RequiredArgsConstructor
    class SubscriptionDto {
        private final ClientId clientId;
        private final ScriptId scriptId;
        private final Description description;
        private final TopicFilter topicFilter;
    }

}
