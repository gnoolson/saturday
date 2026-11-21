package gnoolson.saturday.app.mqtt.broker;

import gnoolson.saturday.broker.model.MQTTBroker;
import gnoolson.saturday.common.model.vo.PositiveNumber;
import io.moquette.broker.Server;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.MemoryConfig;
import io.moquette.broker.security.PermitAllAuthorizatorPolicy;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

@RequiredArgsConstructor
public class MoquetteMQTTBroker implements MQTTBroker {

    private final SaturdayIAuthenticator saturdayIAuthenticator;
    private final SaturdayInterceptor saturdayInterceptor;
    private final boolean useBrokerConfigurationOnly;
    private final MoquettePropertiesConfig moquettePropertiesConfig;

    private Server moquette;
    private boolean active;

    /*
     *
     *
     */
    public void start(PositiveNumber port, boolean allowAnonymousConnections) {
        if(useBrokerConfigurationOnly) {
            startServer();
        } else {
            startServer(port, allowAnonymousConnections);
        }
    }

    @Override
    public void shutdown() {
        active = false;
        if (moquette != null) {
            moquette.stopServer();
            moquette = null;
        }
    }

    @Override
    public boolean isWorking() {
        return active;
    }

    /*
    *
    *
    * */
    private void startServer(PositiveNumber port, boolean allowAnonymousConnections) {
        try {
            Properties configProps = new Properties();
            configProps.put(IConfig.PORT_PROPERTY_NAME, String.valueOf(port.getValue()));
            configProps.put(IConfig.HOST_PROPERTY_NAME, "0.0.0.0");
            configProps.put(IConfig.ALLOW_ANONYMOUS_PROPERTY_NAME, String.valueOf(allowAnonymousConnections));
            configProps.put(IConfig.KEY_STORE_TYPE, "memory");
            configProps.put(IConfig.PERSISTENCE_ENABLED_PROPERTY_NAME, "false");

            Map<String, String> additionalParameters = moquettePropertiesConfig.getConfig();
            for (String key : additionalParameters.keySet()) {
                configProps.put(key, additionalParameters.get(key));
            }

            IConfig memoryConfig = new MemoryConfig(configProps);

            moquette = new Server();
            moquette.startServer(memoryConfig,
                    Collections.singletonList(saturdayInterceptor),
                    null,
                    saturdayIAuthenticator,
                    new PermitAllAuthorizatorPolicy());
            active = true;
        } catch (Exception e) {
            throw new RuntimeException(e); // +
        }
    }

    private void startServer() {
        try {
            Properties configProps = new Properties();

            Map<String, String> additionalParameters = moquettePropertiesConfig.getConfig();
            for (String key : additionalParameters.keySet()) {
                configProps.put(key, additionalParameters.get(key));
            }

            IConfig memoryConfig = new MemoryConfig(configProps);

            moquette = new Server();
            moquette.startServer(memoryConfig);
            active = true;
        } catch (Exception e) {
            throw new RuntimeException(e); // +
        }
    }

}
