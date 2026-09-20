package gnoolson.saturday.schedule.port.outbound;

import gnoolson.saturday.common.model.vo.ScheduleId;
import gnoolson.saturday.schedule.model.vo.CronExpression;

import java.util.concurrent.ScheduledFuture;

public interface ScheduleStarterGateway {

    ScheduledFuture<?> execute(ScheduleId id, Callback callback, CronExpression cronExpression);

    interface Callback {
        void exec();
    }

}
