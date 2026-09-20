package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.Payload;
import gnoolson.saturday.common.model.vo.QoS;
import gnoolson.saturday.common.model.vo.Topic;

// +
public interface PublishMessageUseCase {

    boolean execute(ClientId clientId, Topic topic, Payload payload, QoS qos);

}
