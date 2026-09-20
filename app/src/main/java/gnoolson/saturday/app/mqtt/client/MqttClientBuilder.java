package gnoolson.saturday.app.mqtt.client;

import gnoolson.saturday.client.model.entity.Client;
import org.eclipse.paho.client.mqttv3.IMqttClient;

public interface MqttClientBuilder {

    IMqttClient execute(Client client);

}
