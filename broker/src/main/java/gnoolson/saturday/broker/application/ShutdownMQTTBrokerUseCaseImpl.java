package gnoolson.saturday.broker.application;

import gnoolson.saturday.broker.model.MQTTBroker;
import gnoolson.saturday.broker.model.MQTTBrokerState;
import gnoolson.saturday.broker.port.inbount.ShutdownMQTTBrokerUseCase;
import gnoolson.saturday.broker.port.outbount.MQTTBrokerStateRepositoryGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShutdownMQTTBrokerUseCaseImpl implements ShutdownMQTTBrokerUseCase {

    private final MQTTBroker mqttBroker;
    private final MQTTBrokerStateRepositoryGateway mqttBrokerStateRepositoryGateway;
    private final ClientSessionStorage storage;

    /*
     *
     *
     * */
    @Override
    public void execute() {
        if (!mqttBroker.isWorking())
            throw new IllegalStateException("MQTT broker is already stopped"); // +

        mqttBroker.shutdown();

        MQTTBrokerState previousState = mqttBrokerStateRepositoryGateway.find();
        mqttBrokerStateRepositoryGateway.save(new MQTTBrokerState(
                previousState.getPort(),
                previousState.isAllowAnonymousConnections(),
                false
        ));

        storage.clear();
    }

}
