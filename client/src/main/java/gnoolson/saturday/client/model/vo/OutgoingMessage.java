package gnoolson.saturday.client.model.vo;

import gnoolson.saturday.common.model.vo.Payload;
import gnoolson.saturday.common.model.vo.QoS;
import gnoolson.saturday.common.model.vo.Topic;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class OutgoingMessage {

    private final Topic topic;
    private final Payload payload;
    private final QoS qos;

}
