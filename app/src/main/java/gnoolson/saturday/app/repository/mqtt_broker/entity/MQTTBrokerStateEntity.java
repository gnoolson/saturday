package gnoolson.saturday.app.repository.mqtt_broker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MQTTBrokerStateEntity implements Serializable {

    private boolean started;
    private boolean allowAnonymousConnections;
    private int port;

}
