package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.client.model.vo.ClientAuth;
import gnoolson.saturday.client.model.vo.ServerURI;
import gnoolson.saturday.common.model.vo.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

// +
public interface ImportClientsUseCase {

    void execute(List<ClientDto> clients);

    /*
     *
     *
     * */
    @Getter
    @RequiredArgsConstructor
    class ClientDto {
        private final ClientId id;
        private final ProjectId projectId;
        private final ClientName name;
        private final Description description;
        private final ServerURI serverURI;
        private final ClientAuth clientAuth;
        private final ScriptId clientConnectionEventScriptHandler;
        private final ScriptId clientDisconnectionEventScriptHandler;
    }

}
