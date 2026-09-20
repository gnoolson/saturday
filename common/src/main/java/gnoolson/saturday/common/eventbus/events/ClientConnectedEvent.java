package gnoolson.saturday.common.eventbus.events;

import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ProjectId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class ClientConnectedEvent implements Event {

    private final ProjectId projectId;
    private final ClientId clientId;

}
