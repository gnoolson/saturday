package gnoolson.saturday.app.mqtt.broker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "io.moquette.broker")
public class MoquettePropertiesConfig {

    private Map<String, String> config = new HashMap<>();

}