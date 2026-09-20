package gnoolson.saturday.common.eventbus;


import gnoolson.saturday.common.eventbus.events.Event;

public interface Callback<T extends Event> {

    void exec(T event);

}
