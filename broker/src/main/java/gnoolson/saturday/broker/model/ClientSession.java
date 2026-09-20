package gnoolson.saturday.broker.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class ClientSession {

    private final String clientName;
    private final String username;
    private final long connected;

    /*
     *
     *
     *  */
    public ClientSession(String clientName, String username, long connected) {
        this.clientName = clientName;
        this.username = username == null ? "ANONYMOUS" : username;
        this.connected = connected;
    }

}
