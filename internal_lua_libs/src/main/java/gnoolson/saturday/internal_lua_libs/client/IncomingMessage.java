package gnoolson.saturday.internal_lua_libs.client;

import gnoolson.saturday.common.model.vo.Payload;
import gnoolson.saturday.common.model.vo.QoS;
import gnoolson.saturday.common.model.vo.Topic;
import gnoolson.saturday.common.model.vo.TopicFilter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IncomingMessage {

    @Getter
    private final Topic topic;
    @Getter
    private final TopicFilter topicFilter;
    @Getter
    private final QoS qos;
    @Getter
    private final Payload data;

}
