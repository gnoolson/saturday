package gnoolson.saturday.app.web.client.dto;

import gnoolson.saturday.app.web.client.dto.validation.AuthFieldsValidation;
import gnoolson.saturday.app.web.dto.NotEmptyUUIDValidation;
import gnoolson.saturday.app.web.dto.Regexes;
import gnoolson.saturday.client.port.inbound.GetAllClientsUseCase;
import gnoolson.saturday.client.port.inbound.GetClientUseCase;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ClientName;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
public class ClientDto {

    private UUID id;

    @Pattern(regexp = Regexes.CLIENT_NAME, message = "{client.upsert.form.name.error}")
    private String name;

    @Length(max = 1000)
    private String description;

    @NotBlank
    @Pattern(regexp = Regexes.SERVER_URI, message = "{client.upsert.form.server_uri.error}")
    private String serverURI;

    @AuthFieldsValidation
    private Auth auth;

    @NotNull
    @NotEmptyUUIDValidation(message = "{client.upsert.form.project_id.error}")
    private UUID projectId;

    private String connectionStatus;
    private String connectionError;

    @NotNull
    private UUID connectionEventHandler;

    @NotNull
    private UUID disconnectionEventHandler;

    private long connectionTimestamp;
    private long disconnectionTimestamp;

    /*
     *
     *
     * */
    public ClientDto() {
        this.id = ClientId.empty().getValue();
        this.name = ClientName.random().getValue();
        this.description = StringUtils.EMPTY;
        this.auth = new Auth();
        this.serverURI = "tcp://127.0.0.1:1883";
        this.connectionEventHandler = ScriptId.empty().getValue();
        this.disconnectionEventHandler = ScriptId.empty().getValue();
        this.projectId = ProjectId.empty().getValue();
    }

    public ClientDto(GetClientUseCase.ClientDto clientDto) {
        this.id = clientDto.getId().getValue();
        this.name = clientDto.getName().getValue();
        this.description = clientDto.getDescription().getValue();
        this.serverURI = clientDto.getServerURI().getValue();
        this.auth = new Auth(
                clientDto.getClientAuth().isUse(),
                clientDto.getClientAuth().getUsername().getValue(),
                ""
        );
        this.connectionStatus = "";
        this.connectionError = clientDto.getConnectionError().getMessage();
        this.connectionEventHandler = clientDto.getConnectionEventHandler().getValue();
        this.disconnectionEventHandler = clientDto.getDisconnectionEventHandler().getValue();
        this.projectId = clientDto.getProjectId().getValue();
    }

    public ClientDto(GetAllClientsUseCase.ClientDto clientDto) {
        this.id = clientDto.getId().getValue();
        this.name = clientDto.getName().getValue();
        this.description = clientDto.getDescription().getValue();
        this.serverURI = clientDto.getServerURI().getValue();
        this.auth = new Auth();
        this.connectionStatus = clientDto.getConnectionStatus().toString();
        this.connectionError = clientDto.getConnectionError().getMessage();
        this.connectionEventHandler = ScriptId.empty().getValue();
        this.disconnectionEventHandler = ScriptId.empty().getValue();
        this.connectionTimestamp = clientDto.getConnectionTimestamp().getValue();
        this.disconnectionTimestamp = clientDto.getDisconnectionTimestamp().getValue();
        this.projectId = clientDto.getProjectId().getValue();
    }

    /*
     *
     *
     * */
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Auth {
        private boolean use;
        private String username;
        private String password;
    }

}
