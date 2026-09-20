package gnoolson.saturday.common.eventbus.events;

import gnoolson.saturday.common.model.vo.ProjectId;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class ProjectDeletedEvent implements Event {

    private final ProjectId id;

}
