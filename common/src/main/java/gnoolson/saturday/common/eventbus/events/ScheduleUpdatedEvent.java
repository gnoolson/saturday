package gnoolson.saturday.common.eventbus.events;

import gnoolson.saturday.common.model.vo.ScheduleId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class ScheduleUpdatedEvent implements Event {

    private final ScheduleId scheduleId;

}
