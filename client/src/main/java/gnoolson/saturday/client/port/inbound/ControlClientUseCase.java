package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.common.model.vo.ClientId;

// +
public interface ControlClientUseCase {

    void execute(ClientId id, boolean enable);

}
