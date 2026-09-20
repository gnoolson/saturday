package gnoolson.saturday.client.model.exception;

import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ClientName;

public class ClientNotFountException extends RuntimeException {

    private final ClientId clientId;
    private final ClientName clientName;

    /*
     *
     *
     * */
    public ClientNotFountException(ClientId clientId) {
        this.clientId = clientId;
        this.clientName = null;
    }

    public ClientNotFountException(ClientName clientName) {
        this.clientName = clientName;
        this.clientId = null;
    }

    @Override
    public String getMessage() {
        if (this.clientId != null)
            return String.format("Client \"%s\" was not found", clientId.getValue().toString()); // +

        if (this.clientName != null)
            return String.format("Client \"%s\" was not found", clientName.getValue()); // +

        throw new IllegalStateException(); // +
    }

}
