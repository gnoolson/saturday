package gnoolson.saturday.app.config;

import gnoolson.saturday.client.application.MQTTClientManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ShutdownListener {

    @Autowired
    private MQTTClientManager mqttClientManager;

    /*
     *
     *
     * */
    @EventListener
    public void onShutdown(ContextClosedEvent event) {
        mqttClientManager.shutdown();
    }

}