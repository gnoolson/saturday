package gnoolson.saturday.client.port.inbound;

import gnoolson.saturday.client.model.vo.ConnectionError;
import gnoolson.saturday.client.model.vo.ServerURI;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ClientName;
import gnoolson.saturday.common.model.vo.Description;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.time.vo.TimeInMs;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

// +
public interface GetAllClientsUseCase {

    List<ClientDto> execute();

    /*
    *
    *
    * */
    enum ConnectionStatus {
        CONNECTION,
        CONNECTED,
        DISCONNECTED,
        DISCONNECTION
    }

    @Data
    @RequiredArgsConstructor
    class ClientDto {
        private final ClientId id;
        private final ProjectId projectId;
        private final ClientName name;
        private final Description description;
        private final ServerURI serverURI;
        private final ConnectionStatus connectionStatus;
        private final ConnectionError connectionError;
        private final TimeInMs connectionTimestamp;
        private final TimeInMs disconnectionTimestamp;
    }

}
