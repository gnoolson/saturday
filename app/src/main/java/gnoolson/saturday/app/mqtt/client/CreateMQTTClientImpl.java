package gnoolson.saturday.app.mqtt.client;

import gnoolson.saturday.client.application.MqttClient;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.port.outbound.CreateMQTTClientGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateMQTTClientImpl implements CreateMQTTClientGateway {

    private final PahoGeneralConfig pahoGeneralConfig;

    /*
     *
     *
     * */
    @Override
    public MqttClient execute(Client client) {
        try {
            return new MqttClientImpl(client, pahoGeneralConfig);
        } catch (Exception e) {
            throw new RuntimeException(e); // +
        }
    }

}
