package gnoolson.saturday.schedule.port.inbound;

import gnoolson.saturday.common.model.vo.ScheduleId;


public interface DeleteScheduleUseCase {

    boolean execute(ScheduleId id);

}
