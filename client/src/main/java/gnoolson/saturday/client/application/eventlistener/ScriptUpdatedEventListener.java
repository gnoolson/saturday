package gnoolson.saturday.client.application.eventlistener;

import gnoolson.saturday.client.application.ClientSubscriptionUpdater;
import gnoolson.saturday.common.eventbus.Callback;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ScriptUpdatedEvent;

public class ScriptUpdatedEventListener implements Callback<ScriptUpdatedEvent> {

    private final ClientSubscriptionUpdater clientSubscriptionUpdater;

    /*
     *
     *
     * */
    public ScriptUpdatedEventListener(EventBus eventBus, ClientSubscriptionUpdater clientSubscriptionUpdater) {
        this.clientSubscriptionUpdater = clientSubscriptionUpdater;
        eventBus.on(ScriptUpdatedEvent.class, this);
    }

    @Override
    public void exec(ScriptUpdatedEvent event) {
        clientSubscriptionUpdater.updateAll();
    }

}