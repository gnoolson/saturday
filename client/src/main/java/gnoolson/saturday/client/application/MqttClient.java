package gnoolson.saturday.client.application;

import gnoolson.saturday.client.model.vo.OutgoingMessage;
import gnoolson.saturday.client.model.vo.Subscription;
import gnoolson.saturday.common.model.vo.TopicFilter;

import java.util.List;

public interface MqttClient {

    void connect() throws Exception;

    boolean isConnected();

    void disconnectAndClose();

    void syncSubscriptions(List<Subscription> subscriptions, MessageArrivedCallback messageArrivedCallback);

    boolean publish(OutgoingMessage outgoingMessage);

    boolean isSubscribed(TopicFilter topicFilter);

    String getName();

}
