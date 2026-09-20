package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.client.application.MessageArrivedCallback;
import gnoolson.saturday.client.application.MqttClient;
import gnoolson.saturday.client.model.vo.OutgoingMessage;
import gnoolson.saturday.client.model.vo.Subscription;
import gnoolson.saturday.common.model.vo.TopicFilter;

import java.util.List;

public class MqttClientHelper {

    public static MqttClient createConnected(){
        return new MqttClient() {
            @Override
            public void connect() throws Exception {

            }

            @Override
            public boolean isConnected() {
                return true;
            }

            @Override
            public void disconnectAndClose() {

            }

            @Override
            public void syncSubscriptions(List<Subscription> subscriptions, MessageArrivedCallback messageArrivedCallback) {

            }

            @Override
            public boolean publish(OutgoingMessage outgoingMessage) {
                return false;
            }

            @Override
            public boolean isSubscribed(TopicFilter topicFilter) {
                return false;
            }

            @Override
            public String getName() {
                return "";
            }
        };
    }

    public static MqttClient createDisconnected(){
        return new MqttClient() {
            @Override
            public void connect() throws Exception {

            }

            @Override
            public boolean isConnected() {
                return false;
            }

            @Override
            public void disconnectAndClose() {

            }

            @Override
            public void syncSubscriptions(List<Subscription> subscriptions, MessageArrivedCallback messageArrivedCallback) {

            }

            @Override
            public boolean publish(OutgoingMessage outgoingMessage) {
                return false;
            }

            @Override
            public boolean isSubscribed(TopicFilter topicFilter) {
                return false;
            }

            @Override
            public String getName() {
                return "";
            }
        };
    }


}
