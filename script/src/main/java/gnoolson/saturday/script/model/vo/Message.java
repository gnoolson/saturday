package gnoolson.saturday.script.model.vo;

import gnoolson.saturday.common.model.vo.Payload;
import gnoolson.saturday.common.model.vo.QoS;
import gnoolson.saturday.common.model.vo.Topic;
import gnoolson.saturday.common.validator.DomainModelValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class Message {
    private final Topic topic;
    private final Payload payload;
    private final QoS qos;

    public Message(Topic topic, Payload payload, QoS qos) {
        DomainModelValidator.checkNotNull(topic, "Topic");
        DomainModelValidator.checkNotNull(payload, "Payload");
        DomainModelValidator.checkNotNull(qos, "QoS");

        this.topic = topic;
        this.payload = payload;
        this.qos = qos;
    }
}