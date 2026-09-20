package gnoolson.saturday.app.web.broker.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class StartMQTTBrokerDto {

    private boolean allowAnonymousConnections;

    @NotNull(message = "Port number is required")
    @Min(value = 1, message = "Port must be greater than 0")
    @Max(value = 65535, message = "Port cannot exceed 65535")
    private Integer port;

}
