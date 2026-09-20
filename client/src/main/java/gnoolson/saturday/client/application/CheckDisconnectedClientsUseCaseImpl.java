package gnoolson.saturday.client.application;

import gnoolson.saturday.client.port.inbound.CheckDisconnectedClientsUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckDisconnectedClientsUseCaseImpl implements CheckDisconnectedClientsUseCase {

    private final MQTTClientManager mqttClientManager;

    /*
     *
     *
     * */
    @Override
    public void execute() {
        mqttClientManager.checkConnections();
    }

}
