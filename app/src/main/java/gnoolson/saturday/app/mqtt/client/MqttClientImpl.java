package gnoolson.saturday.app.mqtt.client;

import gnoolson.saturday.client.application.MessageArrivedCallback;
import gnoolson.saturday.client.application.MqttClient;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.model.vo.IncomingMessage;
import gnoolson.saturday.client.model.vo.OutgoingMessage;
import gnoolson.saturday.client.model.vo.Subscription;
import gnoolson.saturday.common.model.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public class MqttClientImpl implements MqttClient {

    private final PahoGeneralConfig pahoGeneralConfig;
    private final MqttAsyncClient innerMqttClient;
    private final MqttConnectOptions options;
    private final List<Subscription> subscriptions = new CopyOnWriteArrayList<>();
    private final ExecutorService callbackExecutor = Executors.newFixedThreadPool(8);

    private volatile MessageArrivedCallback lastCallback;

    /*
     *
     *
     * */
    public MqttClientImpl(Client client, PahoGeneralConfig pahoGeneralConfig) throws MqttException {
        this.innerMqttClient = new MqttAsyncClient(
                client.getServerURI().getValue(),
                client.getName().getValue(),
                new MemoryPersistence() // or MqttDefaultFilePersistence ...
        );

        this.pahoGeneralConfig = pahoGeneralConfig;
        this.options = new MqttConnectOptions();
        this.options.setAutomaticReconnect(false);
        this.options.setCleanSession(pahoGeneralConfig.isCleanSession());
        this.options.setMaxInflight(pahoGeneralConfig.getMaxInflightMessages());
        this.options.setConnectionTimeout(10);
        this.options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);

        DisconnectedBufferOptions bufferOptions = new DisconnectedBufferOptions();
        bufferOptions.setBufferEnabled(true);
        bufferOptions.setBufferSize(pahoGeneralConfig.getMaxBufferSize());
        bufferOptions.setDeleteOldestMessages(pahoGeneralConfig.isDeleteOldestMessages());

        this.innerMqttClient.setBufferOpts(bufferOptions);

        if (client.getClientAuth().isUse()) {
            this.options.setUserName(client.getClientAuth().getUsername().getValue());
            this.options.setPassword(client.getClientAuth().getPassword().getValue().toCharArray());
        }
    }

    @Override
    public synchronized void connect() throws Exception {
        if (innerMqttClient.isConnected()) {
            return;
        }

        innerMqttClient.connect(options).waitForCompletion(pahoGeneralConfig.getActionTimeout());
        restoreSubscriptions();
    }

    @Override
    public boolean isConnected() {
        return innerMqttClient.isConnected();
    }

    @Override
    public void disconnectAndClose() {
        callbackExecutor.shutdownNow();

        try {
            if (innerMqttClient.isConnected()) {
                innerMqttClient.disconnect().waitForCompletion(5000);
            }
        } catch (Exception e) {
            log.warn("Error during MQTT disconnect for client \"{}\": {}", getName(), e.getMessage());
        }

        try {
            innerMqttClient.close();
        } catch (Exception e) {
            log.warn("Error during MQTT close for client \"{}\": {}", getName(), e.getMessage());
        }
    }

    @Override
    public synchronized void syncSubscriptions(List<Subscription> actualSubscriptions, MessageArrivedCallback messageArrivedCallback) {
        this.lastCallback = messageArrivedCallback;
        unsubscribeIfNeeded(actualSubscriptions);
        subscribeIfNeeded(actualSubscriptions, messageArrivedCallback);
    }

    @Override
    public void publish(OutgoingMessage outgoingMessage) {
        try {
            innerMqttClient.publish(
                    outgoingMessage.getTopic().getValue(),
                    outgoingMessage.getPayload().getValue(),
                    outgoingMessage.getQos().getValue(),
                    outgoingMessage.isRetained()
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isSubscribed(TopicFilter topicFilter) {
        for (Subscription subscription : subscriptions) {
            if (subscription.getTopicFilter().equals(topicFilter)) {
                return true;
            }
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
    private void restoreSubscriptions() {
        if (lastCallback == null || subscriptions.isEmpty()) {
            return;
        }

        for (Subscription subscription : subscriptions) {
            registerBrokerSubscription(subscription, lastCallback);
        }
    }

    private void subscribeIfNeeded(List<Subscription> actualSubscriptions, MessageArrivedCallback callback) {
        for (Subscription subscription : actualSubscriptions) {
            if (!isSubscribed(subscription.getTopicFilter())) {
                subscriptions.add(subscription);
                if (innerMqttClient.isConnected()) {
                    registerBrokerSubscription(subscription, callback);
                }
            }
        }
    }

    private void unsubscribeIfNeeded(List<Subscription> actualSubscriptions) {
        for (Subscription subscription : subscriptions) {
            boolean found = actualSubscriptions.stream().anyMatch(actual -> {
                return actual.getTopicFilter().equals(subscription.getTopicFilter()) && actual.getQos().equals(subscription.getQos());
            });

            if (!found) {
                unsubscribe(subscription);
            }
        }
    }

    private void registerBrokerSubscription(Subscription subscription, MessageArrivedCallback callback) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Client \"{}\" subscribes on \"{}\"", getName(), subscription.getTopicFilter().getValue());
            }

            innerMqttClient.subscribe(
                    subscription.getTopicFilter().getValue(),
                    subscription.getQos().getValue(),
                    new Listener(callback, callbackExecutor, subscription.getId(), subscription.getScriptId(), subscription.getTopicFilter())
            ).waitForCompletion(pahoGeneralConfig.getActionTimeout());
        } catch (Exception e) {
            log.warn("Failed to subscribe client \"{}\" to \"{}\": {}", getName(), subscription.getTopicFilter().getValue(), e.getMessage());
        }
    }

    private void unsubscribe(Subscription subscription) {
        subscriptions.remove(subscription);
        if (!innerMqttClient.isConnected()) {
            return;
        }

        try {
            if (log.isDebugEnabled()) {
                log.debug("Client \"{}\" unsubscribes on \"{}\"", getName(), subscription.getTopicFilter().getValue());
            }
            innerMqttClient.unsubscribe(subscription.getTopicFilter().getValue()).waitForCompletion(pahoGeneralConfig.getActionTimeout());
        } catch (Exception e) {
            log.warn("Failed to unsubscribe client \"{}\" from \"{}\": {}", getName(), subscription.getTopicFilter().getValue(), e.getMessage());
        }
    }

    /*
     *
     *
     * */
    @RequiredArgsConstructor
    private static class Listener implements IMqttMessageListener {

        private final MessageArrivedCallback messageArrivedCallback;
        private final ExecutorService executor;
        private final SubscriptionId subscriptionId;
        private final ScriptId scriptId;
        private final TopicFilter topicFilter;

        @Override
        public void messageArrived(String topic, MqttMessage message) {
            executor.submit(() -> {
                try {
                    messageArrivedCallback.execute(
                            subscriptionId,
                            scriptId,
                            new IncomingMessage(
                                    topicFilter,
                                    Topic.of(topic),
                                    Payload.of(message.getPayload()),
                                    QoS.of(message.getQos())
                            )
                    );
                } catch (Exception e) {
                    log.warn("Exception in message callback for script \"{}\": {}", scriptId, e.getMessage());
                }
            });
        }
    }

}
