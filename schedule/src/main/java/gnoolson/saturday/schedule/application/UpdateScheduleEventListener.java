package gnoolson.saturday.schedule.application;

import gnoolson.saturday.common.eventbus.Callback;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ScheduleUpdatedEvent;

public class UpdateScheduleEventListener implements Callback<ScheduleUpdatedEvent> {

    private final ScheduleManager scheduleManager;

    /*
     *
     *
     * */
    public UpdateScheduleEventListener(EventBus eventBus, ScheduleManager scheduleManager) {
        this.scheduleManager = scheduleManager;

        eventBus.on(ScheduleUpdatedEvent.class, this);
    }

    @Override
    public void exec(ScheduleUpdatedEvent event) {
        scheduleManager.cancelIfExists(event.getScheduleId());
    }

}
