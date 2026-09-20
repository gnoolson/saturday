package gnoolson.saturday.schedule.port.inbound;

import gnoolson.saturday.common.model.vo.ScheduleId;


public interface ScheduleControlUseCase {

    void execute(ScheduleId id, boolean flag);

}
