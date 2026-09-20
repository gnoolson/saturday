package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.common.model.vo.ClientId;

// +
public interface DeleteClientUseCase {

    boolean execute(ClientId clientId);

}
