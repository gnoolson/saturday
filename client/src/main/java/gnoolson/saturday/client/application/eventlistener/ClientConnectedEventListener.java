package gnoolson.saturday.client.application.eventlistener;

import gnoolson.saturday.client.application.ClientSubscriptionUpdater;
import gnoolson.saturday.client.application.ClientConnectedEventHandler;
import gnoolson.saturday.client.application.MarkConnected;
import gnoolson.saturday.common.eventbus.Callback;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ClientConnectedEvent;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ClientConnectedEventListener implements Callback<ClientConnectedEvent> {

    private final MarkConnected markConnected;
    private final ClientConnectedEventHandler clientConnectedEventHandler;
    private final ClientSubscriptionUpdater clientSubscriptionUpdater;

    /*
     *
     *
     * */
    public ClientConnectedEventListener(EventBus eventBus,
                                        MarkConnected markConnected,
                                        ClientConnectedEventHandler clientConnectedEventHandler,
                                        ClientSubscriptionUpdater clientSubscriptionUpdater) {

        this.markConnected = markConnected;
        this.clientConnectedEventHandler = clientConnectedEventHandler;
        this.clientSubscriptionUpdater = clientSubscriptionUpdater;

        eventBus.on(ClientConnectedEvent.class, this);
    }

    @Override
    public void exec(ClientConnectedEvent event) {
        markConnected.exec(event.getClientId());
        clientConnectedEventHandler.execute(event.getClientId());
        clientSubscriptionUpdater.updateSubscriptionsForClient(event.getClientId());
    }

}
