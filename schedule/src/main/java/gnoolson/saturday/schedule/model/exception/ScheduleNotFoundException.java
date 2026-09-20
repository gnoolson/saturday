package gnoolson.saturday.schedule.model.exception;

import gnoolson.saturday.common.model.vo.ScheduleId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScheduleNotFoundException extends RuntimeException {

    private final ScheduleId scheduleId;

    @Override
    public String getMessage() {
        return String.format("Schedule \"%s\" was not found", scheduleId.getValue().toString());
    }

}
