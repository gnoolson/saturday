package gnoolson.saturday.common.eventbus.events;

import gnoolson.saturday.common.model.vo.ScriptId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class ScriptUpdatedEvent implements Event {

    private final ScriptId scriptId;

}
