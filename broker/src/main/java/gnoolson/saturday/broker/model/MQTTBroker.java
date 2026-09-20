package gnoolson.saturday.broker.model;

import gnoolson.saturday.common.model.vo.PositiveNumber;

public interface MQTTBroker {

    void start(PositiveNumber port, boolean allowAnonymousConnections);

    void shutdown();

    boolean isWorking();

}
