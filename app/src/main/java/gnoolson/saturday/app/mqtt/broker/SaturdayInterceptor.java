package gnoolson.saturday.app.mqtt.broker;

import gnoolson.saturday.broker.application.ClientSessionStorage;
import gnoolson.saturday.broker.model.ClientSession;
import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.messages.InterceptConnectMessage;
import io.moquette.interception.messages.InterceptDisconnectMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaturdayInterceptor extends AbstractInterceptHandler  {

    private final ClientSessionStorage clientSessionStorage;

    /*
     *
     *
     * */
    @Override
    public String getID() {
        return "client-listener";
    }

    @Override
    public void onConnect(InterceptConnectMessage msg) {
        clientSessionStorage.add(msg.getClientID(), msg.getUsername());
    }

    @Override
    public void onDisconnect(InterceptDisconnectMessage msg) {
        clientSessionStorage.remove(msg.getClientID());
    }

    @Override
    public void onSessionLoopError(Throwable throwable) {
    }

}