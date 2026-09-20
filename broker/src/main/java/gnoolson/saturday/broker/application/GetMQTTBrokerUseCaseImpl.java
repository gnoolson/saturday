package gnoolson.saturday.broker.application;

import gnoolson.saturday.broker.model.MQTTBrokerState;
import gnoolson.saturday.broker.port.inbount.GetMQTTBrokerUseCase;
import gnoolson.saturday.broker.port.outbount.MQTTBrokerStateRepositoryGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetMQTTBrokerUseCaseImpl implements GetMQTTBrokerUseCase {

    private final MQTTBrokerStateRepositoryGateway mqttBrokerStateRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public BrokerDto execute() {
        MQTTBrokerState mqttBrokerState = mqttBrokerStateRepositoryGateway.find();

        return new BrokerDto(
                mqttBrokerState.isStarted(),
                mqttBrokerState.isAllowAnonymousConnections(),
                mqttBrokerState.getPort()
        );
    }

}
