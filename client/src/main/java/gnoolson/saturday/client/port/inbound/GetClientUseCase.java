package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.client.model.vo.ClientAuth;
import gnoolson.saturday.client.model.vo.ConnectionError;
import gnoolson.saturday.client.model.vo.ServerURI;
import gnoolson.saturday.common.model.vo.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

// +
public interface GetClientUseCase {

    ClientDto execute(ClientId clientId);

    /*
     *
     *
     * */
    @Data
    @RequiredArgsConstructor
    class ClientDto {
        private final ClientId id;
        private final ProjectId projectId;
        private final ClientName name;
        private final Description description;
        private final ServerURI serverURI;
        private final ClientAuth clientAuth;
        private final ConnectionError connectionError;
        private final ScriptId connectionEventHandler;
        private final ScriptId disconnectionEventHandler;
    }

}
