package gnoolson.saturday.subscription.port.inbound;

import gnoolson.saturday.common.model.vo.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public interface GetSubscriptionUseCase {

    SubscriptionDto execute(SubscriptionId subscriptionId);

    /*
     *
     *
     * */
    @Getter
    @RequiredArgsConstructor
    class SubscriptionDto {
        private final SubscriptionId id;
        private final ClientId clientId;
        private final ScriptId scriptId;
        private final Description description;
        private final TopicFilter topicFilter;
        private final ProjectId projectId;
    }

}
