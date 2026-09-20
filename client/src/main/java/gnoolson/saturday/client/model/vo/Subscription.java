package gnoolson.saturday.client.model.vo;

import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.SubscriptionId;
import gnoolson.saturday.common.model.vo.TopicFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
public class Subscription {

    private final SubscriptionId id;
    private final TopicFilter topicFilter;
    private final ScriptId scriptId;

}
