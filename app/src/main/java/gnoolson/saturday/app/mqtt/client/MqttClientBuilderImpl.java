package gnoolson.saturday.app.mqtt.client;

import gnoolson.saturday.client.model.entity.Client;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

@Component
public class MqttClientBuilderImpl implements MqttClientBuilder {

    @Override
    public MqttClient execute(Client client) {
        try {
            return new MqttClient(client.getServerURI().getValue(), client.getName().getValue(), new MemoryPersistence());
        } catch (Exception e) {
            throw new RuntimeException(e); // +
        }
    }

}
