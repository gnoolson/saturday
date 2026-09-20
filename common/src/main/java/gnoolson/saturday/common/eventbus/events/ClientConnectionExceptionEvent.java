package gnoolson.saturday.common.eventbus.events;

import gnoolson.saturday.common.model.vo.ClientId;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class ClientConnectionExceptionEvent implements Event {

    private final ClientId clientId;
    private final Exception exception;

    public ClientConnectionExceptionEvent(ClientId clientId, Exception exception) {
        this.clientId = clientId;
        this.exception = exception;
    }

}
