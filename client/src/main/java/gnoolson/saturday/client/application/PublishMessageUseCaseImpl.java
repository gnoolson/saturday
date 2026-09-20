package gnoolson.saturday.client.application;

import gnoolson.saturday.client.model.vo.OutgoingMessage;
import gnoolson.saturday.client.port.inbound.PublishMessageUseCase;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.Payload;
import gnoolson.saturday.common.model.vo.QoS;
import gnoolson.saturday.common.model.vo.Topic;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PublishMessageUseCaseImpl implements PublishMessageUseCase {

    private final MQTTClientManager mqttClientManager;

    /*
     *
     *
     * */
    @Override
    public boolean execute(ClientId clientId, Topic topic, Payload payload, QoS qos) {
        return mqttClientManager.publish(clientId, new OutgoingMessage(topic, payload, qos));
    }

}
