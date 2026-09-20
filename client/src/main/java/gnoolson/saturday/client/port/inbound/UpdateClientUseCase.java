package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.client.model.vo.ClientAuth;
import gnoolson.saturday.client.model.vo.ServerURI;
import gnoolson.saturday.common.model.vo.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

// +
public interface UpdateClientUseCase {

    void execute(ClientDto clientDto);

    /*
     *
     *
     * */
    @RequiredArgsConstructor
    @Data
    class ClientDto {
        private final ClientId id;
        private final ProjectId projectId;
        private final ClientName name;
        private final Description description;
        private final ServerURI serverUri;
        private final ClientAuth clientAuth;
        private final ScriptId clientConnectionEventScriptHandler;
        private final ScriptId clientDisconnectionEventScriptHandler;
    }

}
