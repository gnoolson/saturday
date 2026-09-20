package gnoolson.saturday.client.application.eventlistener;

import gnoolson.saturday.client.application.ClientSubscriptionUpdater;
import gnoolson.saturday.common.eventbus.Callback;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ClientSubscriptionUpdated;

public class ClientSubscriptionUpdatedEventListener implements Callback<ClientSubscriptionUpdated> {

    private final ClientSubscriptionUpdater clientSubscriptionUpdater;

    /*
     *
     *
     * */
    public ClientSubscriptionUpdatedEventListener(EventBus localEventBus, ClientSubscriptionUpdater clientSubscriptionUpdater) {
        this.clientSubscriptionUpdater = clientSubscriptionUpdater;
        localEventBus.on(ClientSubscriptionUpdated.class, this);
    }

    @Override
    public void exec(ClientSubscriptionUpdated event) {
        clientSubscriptionUpdater.updateSubscriptionsForClient(event.getClientId());
    }

}
