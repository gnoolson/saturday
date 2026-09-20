package gnoolson.saturday.client.model.entity;

import gnoolson.saturday.client.model.vo.ClientAuth;
import gnoolson.saturday.client.model.vo.ConnectionError;
import gnoolson.saturday.client.model.vo.ServerURI;
import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.common.time.vo.TimeInMs;
import gnoolson.saturday.common.validator.DomainModelValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class Client {

    private final ClientId id;
    private final ProjectId projectId;
    private ClientName name;
    private Description description;
    private boolean enabled;
    private ServerURI serverURI;
    private ClientAuth clientAuth;
    private TimeInMs connectedAt;
    private TimeInMs disconnectedAt;
    private ConnectionError connectionError;
    private ScriptId connectedEventHandler;
    private ScriptId disconnectedEventHandler;

    /*
     *
     *
     * */
    public Client(ProjectId projectId,
                  ClientName name,
                  Description description,
                  ServerURI serverUri,
                  ClientAuth clientAuth,
                  ScriptId connectedEventHandler,
                  ScriptId disconnectedEventHandler) {

        this(ClientId.empty(), projectId, name, description, false, serverUri, clientAuth, TimeInMs.zero(), TimeInMs.zero(), ConnectionError.empty(), connectedEventHandler, disconnectedEventHandler);
    }

    public Client(ClientId id,
                  ProjectId projectId,
                  ClientName name,
                  Description description,
                  boolean enabled,
                  ServerURI serverURI,
                  ClientAuth clientAuth,
                  TimeInMs connectedAt,
                  TimeInMs disconnectedAt,
                  ConnectionError connectionError,
                  ScriptId connectedEventHandler,
                  ScriptId disconnectedEventHandler) {

        DomainModelValidator.checkNotNull(id, "ClientId");
        DomainModelValidator.checkNotNull(projectId, "ProjectId");
        DomainModelValidator.checkNotNull(name, "ClientName");
        DomainModelValidator.checkNotNull(description, "Description");
        DomainModelValidator.checkNotNull(serverURI, "ServerURI");
        DomainModelValidator.checkNotNull(clientAuth, "ClientAuth");
        DomainModelValidator.checkNotNull(connectedAt, "ConnectedAt");
        DomainModelValidator.checkNotNull(disconnectedAt, "DisconnectedAt");
        DomainModelValidator.checkNotNull(connectionError, "ConnectionError");
        DomainModelValidator.checkNotNull(connectedEventHandler, "ConnectedEventHandler");
        DomainModelValidator.checkNotNull(disconnectedEventHandler, "DisconnectedEventHandler");

        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.serverURI = serverURI;
        this.clientAuth = clientAuth;
        this.connectedAt = connectedAt;
        this.disconnectedAt = disconnectedAt;
        this.connectionError = connectionError;
        this.connectedEventHandler = connectedEventHandler;
        this.disconnectedEventHandler = disconnectedEventHandler;
        this.enabled = enabled;
    }

    public void disable() {
        this.enabled = false;
    }

    public void enable() {
        this.enabled = true;
    }

    public void markConnected(TimeInMs timeInMs) {
        DomainModelValidator.checkNotNull(timeInMs, "Timestamp");
        this.connectedAt = timeInMs;
    }

    public void markDisconnected(TimeInMs timeInMs) {
        DomainModelValidator.checkNotNull(timeInMs, "Timestamp");
        this.disconnectedAt = timeInMs;
    }

    public void setConnectionErrorInformation(Exception exception, TimeInMs timeInMs) {
        this.connectionError = ConnectionError.of(exception, timeInMs);
    }

    public void eraseConnectionError() {
        this.connectionError = ConnectionError.empty();
    }

    public void update(ClientName name, Description description, ServerURI serverUri, ClientAuth clientAuth, ScriptId connectedEventHandler, ScriptId disconnectedEventHandler) {
        DomainModelValidator.checkNotNull(name, "ClientName");
        DomainModelValidator.checkNotNull(description, "Description");
        DomainModelValidator.checkNotNull(serverUri, "ServerURI");
        DomainModelValidator.checkNotNull(clientAuth, "ClientAuth");
        DomainModelValidator.checkNotNull(connectedEventHandler, "ConnectedEventHandler");
        DomainModelValidator.checkNotNull(disconnectedEventHandler, "DisconnectedEventHandler");

        this.name = name;
        this.description = description;
        this.serverURI = serverUri;
        this.clientAuth = clientAuth;
        this.connectedEventHandler = connectedEventHandler;
        this.disconnectedEventHandler = disconnectedEventHandler;
    }

}
