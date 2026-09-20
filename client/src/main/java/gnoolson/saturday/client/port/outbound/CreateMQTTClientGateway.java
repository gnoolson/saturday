package gnoolson.saturday.client.port.outbound;

import gnoolson.saturday.client.application.MqttClient;
import gnoolson.saturday.client.model.entity.Client;


public interface CreateMQTTClientGateway {

    MqttClient execute(Client client);

}
