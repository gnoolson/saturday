package gnoolson.saturday.schedule.application;

import gnoolson.saturday.common.model.vo.ScheduleId;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

public class ScheduledFutureRepositor {

    private final Map<ScheduleId, ScheduledFuture<?>> activeSchedules = new ConcurrentHashMap<>();

    /*
     *
     *
     * */
    public boolean exists(ScheduleId scheduleId) {
        return activeSchedules.containsKey(scheduleId);
    }

    public void save(ScheduleId id, ScheduledFuture<?> scheduledFuture) {
        activeSchedules.put(id, scheduledFuture);
    }

    public Set<ScheduleId> selectIds() {
        return activeSchedules.keySet();
    }

    public Optional<ScheduledFuture<?>> get(ScheduleId scheduleId) {
        ScheduledFuture<?> scheduledFuture = activeSchedules.get(scheduleId);
        return Optional.ofNullable(scheduledFuture);
    }

    public Optional<ScheduledFuture<?>> remove(ScheduleId scheduleId) {
        ScheduledFuture<?> scheduledFuture = activeSchedules.remove(scheduleId);
        return Optional.ofNullable(scheduledFuture);
    }

}
