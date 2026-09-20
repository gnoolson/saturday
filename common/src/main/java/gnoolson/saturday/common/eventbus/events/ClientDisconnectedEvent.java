package gnoolson.saturday.common.eventbus.events;

import gnoolson.saturday.common.model.vo.ClientId;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class ClientDisconnectedEvent implements Event {

    private final ClientId clientId;

    public ClientDisconnectedEvent(ClientId clientId) {
        this.clientId = clientId;
    }

}
