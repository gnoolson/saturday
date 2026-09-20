package gnoolson.saturday.app.mqtt.client;

import gnoolson.saturday.client.application.MessageArrivedCallback;
import gnoolson.saturday.client.application.MqttClient;
import gnoolson.saturday.client.model.vo.IncomingMessage;
import gnoolson.saturday.client.model.vo.OutgoingMessage;
import gnoolson.saturday.client.model.vo.Subscription;
import gnoolson.saturday.common.model.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Log4j2
public class MqttClientWrapper implements MqttClient {

    private final IMqttClient innerMqttClient;
    private final MqttConnectOptions options;
    private final List<Subscription> subscriptions = new CopyOnWriteArrayList<>();

    /*
     *
     *
     * */
    public MqttClientWrapper(IMqttClient innerMqttClient) {
        this.innerMqttClient = innerMqttClient;
        this.options = new MqttConnectOptions();
        this.options.setAutomaticReconnect(false);
        this.options.setCleanSession(true);
        this.options.setConnectionTimeout(10);
        this.options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
    }

    public MqttClientWrapper(IMqttClient innerMqttClient, String username, String password) {
        this(innerMqttClient);
        this.options.setUserName(username);
        this.options.setPassword(password.toCharArray());
    }

    @Override
    public void connect() throws Exception {
        innerMqttClient.connect(options);
    }

    @Override
    public boolean isConnected() {
        return innerMqttClient.isConnected();
    }

    @Override
    public void disconnectAndClose() {
        try {
            if (innerMqttClient.isConnected())
                innerMqttClient.disconnect();

            innerMqttClient.close();
        } catch (Exception e) {
            log.warn("Exception", e);
        }
    }

    @Override
    public void syncSubscriptions(List<Subscription> subscriptions, MessageArrivedCallback messageArrivedCallback) {
        subscribeIfNeeded(subscriptions, messageArrivedCallback);
        unsubscribeIfNeeded(subscriptions);
    }

    @Override
    public boolean publish(OutgoingMessage outgoingMessage) {
        try {
            innerMqttClient.publish(outgoingMessage.getTopic().getValue(),
                    outgoingMessage.getPayload().getValue(),
                    outgoingMessage.getQos().getValue(), false);
            return true;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.warn("Exception", e);
            return false;
        }
    }

    @Override
    public boolean isSubscribed(TopicFilter topicFilter) {
        for (Subscription subscription : subscriptions) {
            if (subscription.getTopicFilter().equals(topicFilter))
                return true;
        }

        return false;
    }

    @Override
    public String getName() {
        return innerMqttClient.getClientId();
    }

    /*
     *
     *
     * */
    private void subscribeIfNeeded(List<Subscription> actualSubscriptions, MessageArrivedCallback messageArrivedCallback) {
        for (Subscription subscription : actualSubscriptions) {
            if (!isSubscribed(subscription.getTopicFilter()))
                subscribe(subscription, messageArrivedCallback);
        }
    }

    private void unsubscribeIfNeeded(List<Subscription> actualSubscriptions) {
        for (Subscription subscription : subscriptions) {
            boolean found = false;
            for (Subscription actualSubscription : actualSubscriptions) {
                if (subscription.getTopicFilter().equals(actualSubscription.getTopicFilter())) {
                    found = true;
                    break;
                }
            }
            if (!found)
                unsubscribe(subscription);
        }
    }

    private boolean subscribe(Subscription subscription, MessageArrivedCallback messageArrivedCallback) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Client \"{}\" subscribes on \"{}\"", innerMqttClient.getClientId(), subscription.getTopicFilter().getValue());
            }

            innerMqttClient.subscribe(subscription.getTopicFilter().getValue(), new Listener(messageArrivedCallback, subscription.getId(), subscription.getScriptId(), subscription.getTopicFilter()));
            subscriptions.add(subscription);
            return true;
        } catch (Exception e) {
            log.warn("Exception", e);
            return false;
        }
    }

    private boolean unsubscribe(Subscription subscription) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Client \"{}\" unsubscribes on \"{}\"", innerMqttClient.getClientId(), subscription.getTopicFilter().getValue());
            }

            innerMqttClient.unsubscribe(subscription.getTopicFilter().getValue());
            subscriptions.remove(subscription);
            return true;
        } catch (Exception e) {
            log.warn("Exception", e);
        }
        return false;
    }

    /*
     *
     *
     * */
    @RequiredArgsConstructor
    private static class Listener implements IMqttMessageListener {

        private final MessageArrivedCallback messageArrivedCallback;
        private final SubscriptionId subscriptionId;
        private final ScriptId scriptId;
        private final TopicFilter topicFilter;

        /*
         *
         *
         * */
        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            new Thread(() -> {
                try {
                    messageArrivedCallback.execute(subscriptionId, scriptId, new IncomingMessage(topicFilter, Topic.of(topic), Payload.of(message.getPayload()), QoS.of(message.getQos())));
                } catch (Exception e) {
                    log.warn("Exception", e);
                }
            }).start();
        }
    }

}
