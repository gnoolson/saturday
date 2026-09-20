package gnoolson.saturday.schedule.application;

import gnoolson.saturday.common.model.vo.ScheduleId;
import gnoolson.saturday.schedule.model.entity.Schedule;
import gnoolson.saturday.schedule.port.outbound.ScheduleScriptLauncherGateway;
import gnoolson.saturday.schedule.port.outbound.ScheduleStarterGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

@Log4j2
@RequiredArgsConstructor
public class ScheduleManager {

    private final ScheduleStarterGateway scheduleStarterGateway;
    private final ScheduledFutureRepositor scheduledFutureRepositor;
    private final ScheduleScriptLauncherGateway scheduleScriptLauncherGateway;

    /*
     *
     *
     * */
    public void syncEnabledSchedules(List<Schedule> schedules) {
        plainIfNeed(schedules);
        cancelIfNeed(schedules);
    }

    public void cancelIfExists(ScheduleId scheduleId) {
        if (scheduledFutureRepositor.exists(scheduleId))
            cancel(scheduleId);
    }

    public void cancelAll() {
        for (ScheduleId scheduleId : scheduledFutureRepositor.selectIds()) {
            cancel(scheduleId);
        }
    }

    /*
     *
     *
     * */
    private void plainIfNeed(List<Schedule> schedules) {
        for (Schedule schedule : schedules) {
            if (scheduledFutureRepositor.exists(schedule.getId()))
                continue;

            plan(schedule);
        }
    }

    private void plan(Schedule schedule) {
        if (log.isDebugEnabled())
            log.debug("Schedule \"{}\" is planed", schedule.getId().getValue());

        ScheduledFuture<?> execute = scheduleStarterGateway.execute(schedule.getId(), () -> {
            scheduleScriptLauncherGateway.execute(schedule.getScriptId(), schedule.getId());
        }, schedule.getCronExpression());

        scheduledFutureRepositor.save(schedule.getId(), execute);
    }

    private void cancelIfNeed(List<Schedule> schedules) {
        for (ScheduleId scheduleId : scheduledFutureRepositor.selectIds()) {
            boolean found = false;

            for (Schedule schedule : schedules) {
                if (schedule.getId().equals(scheduleId)) {
                    found = true;
                    break;
                }
            }

            if (!found)
                cancel(scheduleId);
        }
    }

    private void cancel(ScheduleId scheduleId) {
        if (log.isDebugEnabled())
            log.debug("Schedule \"{}\" is canceled", scheduleId.getValue());

        Optional<ScheduledFuture<?>> scheduledFutureOpt = scheduledFutureRepositor.remove(scheduleId);
        scheduledFutureOpt.ifPresent((scheduledFuture) -> {
            scheduledFuture.cancel(false);
        });
    }


}
