package gnoolson.saturday.subscription.port.inbound;

import gnoolson.saturday.common.model.vo.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


public interface ImportSubscriptionsUseCase {

    void execute(List<SubscriptionDto> subscriptions);

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
    }

}
