package gnoolson.saturday.app.mqtt.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class PahoGeneralConfig {

    @Value("${gnoolson.saturday.client.paho.max_buffer_size}")
    private int maxBufferSize;

    @Value("${gnoolson.saturday.client.paho.max_inflight_messages}")
    private int maxInflightMessages;

    @Value("${gnoolson.saturday.client.paho.action_timeout}")
    private long actionTimeout;

    @Value("${gnoolson.saturday.client.paho.connection_timeout}")
    private int connectionTimeout;

    @Value("${gnoolson.saturday.client.paho.delete_oldest_messages}")
    private boolean deleteOldestMessages;

    @Value("${gnoolson.saturday.client.paho.clean_session}")
    private boolean cleanSession;

}
