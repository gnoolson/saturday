package gnoolson.saturday.broker.port.outbount;

import gnoolson.saturday.broker.model.MQTTBrokerState;

public interface MQTTBrokerStateRepositoryGateway {

    void save(MQTTBrokerState mqttBrokerState);

    MQTTBrokerState find();

}
