package gnoolson.saturday.client.application.eventlistener;

import gnoolson.saturday.client.application.ClientDisconnectedEventHandler;
import gnoolson.saturday.client.application.MarkDisconnected;
import gnoolson.saturday.common.eventbus.Callback;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ClientDisconnectedEvent;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ClientDisconnectedEventListener implements Callback<ClientDisconnectedEvent> {

    private final MarkDisconnected markDisconnected;
    private final ClientDisconnectedEventHandler clientDisconnectedEventHandler;

    /*
     *
     *
     * */
    public ClientDisconnectedEventListener(EventBus eventBus,
                                           MarkDisconnected markDisconnected,
                                           ClientDisconnectedEventHandler clientDisconnectedEventHandler) {

        this.markDisconnected = markDisconnected;
        this.clientDisconnectedEventHandler = clientDisconnectedEventHandler;

        eventBus.on(ClientDisconnectedEvent.class, this);
    }

    @Override
    public void exec(ClientDisconnectedEvent event) {
        markDisconnected.exec(event.getClientId());
        clientDisconnectedEventHandler.execute(event.getClientId());
    }

}