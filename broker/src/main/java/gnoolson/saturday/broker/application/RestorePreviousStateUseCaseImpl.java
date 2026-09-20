package gnoolson.saturday.broker.application;

import gnoolson.saturday.broker.model.MQTTBrokerState;
import gnoolson.saturday.broker.port.inbount.RestorePreviousStateUseCase;
import gnoolson.saturday.broker.port.inbount.StartMQTTBrokerUseCase;
import gnoolson.saturday.broker.port.outbount.MQTTBrokerStateRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class RestorePreviousStateUseCaseImpl implements RestorePreviousStateUseCase {

    private final MQTTBrokerStateRepositoryGateway mqttBrokerStateRepositoryGateway;
    private final StartMQTTBrokerUseCase startMQTTBrokerUseCase;

    /*
     *
     *
     * */
    @Override
    public void execute() {
        try {
            MQTTBrokerState mqttBrokerState = mqttBrokerStateRepositoryGateway.find();

            if (mqttBrokerState.isStarted()) {
                startMQTTBrokerUseCase.execute(new StartMQTTBrokerUseCase.StartMQTTBrokerDto(
                        mqttBrokerState.isAllowAnonymousConnections(),
                        mqttBrokerState.getPort()
                ));
            }
        } catch (Exception e) {
            log.warn(e);
        }
    }

}
