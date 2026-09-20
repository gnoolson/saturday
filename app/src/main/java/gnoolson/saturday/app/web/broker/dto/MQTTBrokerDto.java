package gnoolson.saturday.app.web.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MQTTBrokerDto {

    private boolean started;
    private boolean allowAnonymousConnections;
    private int port;

}
