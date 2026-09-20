package gnoolson.saturday.common.eventbus.events;

import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ProjectId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class ClientSubscriptionUpdated implements Event {

    private final ProjectId projectId;
    private final ClientId clientId;

}
