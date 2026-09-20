package gnoolson.saturday.app.project;

import gnoolson.saturday.common.eventbus.Callback;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ProjectDeletedEvent;
import gnoolson.saturday.schedule.application.ScheduleManager;
import org.springframework.stereotype.Component;

@Component
public class ProjectDeletedEventListener implements Callback<ProjectDeletedEvent> {

    private final ScheduleManager scheduleManager;

    /*
     *
     *
     * */
    public ProjectDeletedEventListener(ScheduleManager scheduleManager, EventBus eventBus) {
        this.scheduleManager = scheduleManager;
        eventBus.on(ProjectDeletedEvent.class, this);
    }

    @Override
    public void exec(ProjectDeletedEvent event) {
        scheduleManager.cancelAll();
    }

}
