package gnoolson.saturday.broker.port.outbount;

import gnoolson.saturday.common.model.vo.AuthUsername;
import gnoolson.saturday.common.model.vo.ClientName;
import gnoolson.saturday.common.time.vo.TimeInMs;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

public interface ConnectedClientsProviderGateway {

    List<ClientDto> execute();

    @Getter
    @RequiredArgsConstructor
    class ClientDto {
        private final ClientName name;
        private final AuthUsername username;
        private final TimeInMs connected;
    }

}
