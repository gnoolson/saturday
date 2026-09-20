package gnoolson.saturday.common.eventbus;

import gnoolson.saturday.common.eventbus.events.Event;

public interface EventBus {

    <T extends Event> void emit(T event);

    <T extends Event> void on(Class<T> clazz, Callback<T> callback);

}
