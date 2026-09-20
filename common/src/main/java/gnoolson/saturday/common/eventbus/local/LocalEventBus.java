package gnoolson.saturday.common.eventbus.local;

import gnoolson.saturday.common.eventbus.Callback;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.Event;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class LocalEventBus implements EventBus {

    private final Map<Class<? extends Event>, List<Callback>> events = new HashMap<>();

    /*
     *
     *
     * */
    @Override
    public <T extends Event> void emit(T event) {
        List<Callback> callbacks = events.get(event.getClass());
        if (callbacks == null)
            return;

        for (Callback callback : callbacks) {
            try {
                callback.exec(event);
            } catch (Exception e) {
                log.warn("Exception", e);
            }
        }
    }

    @Override
    public synchronized <T extends Event> void on(Class<T> clazz, Callback<T> callback) {
        List<Callback> callbacks = events.get(clazz);
        if (callbacks == null) {
            callbacks = new ArrayList<>(1);
            events.put(clazz, callbacks);
        }
        callbacks.add(callback);
    }

}
