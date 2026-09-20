package gnoolson.saturday.broker.application;

import gnoolson.saturday.broker.model.MQTTBroker;
import gnoolson.saturday.broker.model.MQTTBrokerState;
import gnoolson.saturday.broker.port.inbount.StartMQTTBrokerUseCase;
import gnoolson.saturday.broker.port.outbount.MQTTBrokerStateRepositoryGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StartMQTTBrokerUseCaseImpl implements StartMQTTBrokerUseCase {

    private final MQTTBroker mqttBroker;
    private final MQTTBrokerStateRepositoryGateway mqttBrokerStateRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public void execute(StartMQTTBrokerDto startMQTTBrokerDto) {
        if (mqttBroker.isWorking())
            throw new IllegalStateException("MQTT broker is already running"); // +

        mqttBroker.start(startMQTTBrokerDto.getPort(), startMQTTBrokerDto.isAllowAnonymousConnections());

        mqttBrokerStateRepositoryGateway.save(new MQTTBrokerState(
                startMQTTBrokerDto.getPort(),
                startMQTTBrokerDto.isAllowAnonymousConnections(),
                true
        ));
    }

}
