package gnoolson.saturday.app.mqtt.client;

import gnoolson.saturday.client.application.MqttClient;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.port.outbound.CreateMQTTClientGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateMQTTClientImpl implements CreateMQTTClientGateway {

    private final MqttClientBuilder mqttClientBuilder;

    /*
     *
     *
     * */
    @Override
    public MqttClient execute(Client client) {
        if (client.getClientAuth().isUse()) {
            return new MqttClientWrapper(
                    mqttClientBuilder.execute(client),
                    client.getClientAuth().getUsername().getValue(),
                    client.getClientAuth().getPassword().getValue()
            );
        } else {
            return new MqttClientWrapper(
                    mqttClientBuilder.execute(client)
            );
        }
    }

}
