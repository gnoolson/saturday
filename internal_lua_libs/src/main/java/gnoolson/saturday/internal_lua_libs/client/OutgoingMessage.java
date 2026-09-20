package gnoolson.saturday.internal_lua_libs.client;

import gnoolson.saturday.common.model.vo.QoS;
import gnoolson.saturday.common.model.vo.Topic;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OutgoingMessage {

    private final Topic topic;
    private final QoS qos;
    private final byte[] payload;

}
