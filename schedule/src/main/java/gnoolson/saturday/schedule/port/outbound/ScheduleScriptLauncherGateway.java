package gnoolson.saturday.schedule.port.outbound;

import gnoolson.saturday.common.model.vo.ScheduleId;
import gnoolson.saturday.common.model.vo.ScriptId;

public interface ScheduleScriptLauncherGateway {

    void execute(ScriptId scriptId, ScheduleId scheduleId);

}
