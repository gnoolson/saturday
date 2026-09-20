package gnoolson.saturday.subscription.port.inbound;

import gnoolson.saturday.common.model.vo.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


public interface GetAllSubscriptionsUseCase {

    List<SubscriptionDto> execute(ClientId clientId);

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
