package gnoolson.saturday.app.scheduler;

import gnoolson.saturday.common.model.vo.ScheduleId;
import gnoolson.saturday.schedule.model.vo.CronExpression;
import gnoolson.saturday.schedule.port.outbound.ScheduleStarterGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
@Component
public class ScheduleStarter implements ScheduleStarterGateway {

    private final TaskScheduler taskScheduler;

    /*
     *
     *
     * */
    @Override
    public ScheduledFuture<?> execute(ScheduleId id, Callback callback, CronExpression cronExpression) {
        return taskScheduler.schedule(
                new SingleInstanceTask(id, callback::exec),
                new CronTrigger(cronExpression.getValue()));
    }

    /*
     *
     *
     * */
    @Log4j2
    public static class SingleInstanceTask implements Runnable {
        private final Runnable targetTask;
        private final AtomicBoolean isRunning = new AtomicBoolean(false);
        private final ScheduleId id;

        /*
         *
         *
         * */
        public SingleInstanceTask(ScheduleId id, Runnable targetTask) {
            this.targetTask = targetTask;
            this.id = id;
        }

        @Override
        public void run() {
            if (!isRunning.compareAndSet(false, true)) {
                log.warn("The scheduled task was skipped. Schedule id : \"{}\"", id.getValue().toString());
                return;
            }

            try {
                targetTask.run();
            } finally {
                isRunning.set(false);
            }
        }
    }

}
