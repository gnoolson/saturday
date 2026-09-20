package gnoolson.saturday.app.repository.mqtt_broker;

import gnoolson.saturday.app.repository.key_value.KVJPARepository;
import gnoolson.saturday.app.repository.key_value.entity.KVEntity;
import gnoolson.saturday.app.repository.mqtt_broker.entity.MQTTBrokerStateEntity;
import gnoolson.saturday.app.utils.JSON;
import gnoolson.saturday.broker.model.MQTTBrokerState;
import gnoolson.saturday.broker.port.outbount.MQTTBrokerStateRepositoryGateway;
import gnoolson.saturday.common.model.vo.PositiveNumber;

import java.util.Optional;


public class MQTTBrokerStateRepositoryGatewayImpl implements MQTTBrokerStateRepositoryGateway {

    private final String KEY = "BROKER";
    private final KVJPARepository kvjpaRepository;
    private MQTTBrokerStateEntity entity = new MQTTBrokerStateEntity(false, true, 1883);

    /*
     *
     *
     * */
    public MQTTBrokerStateRepositoryGatewayImpl(KVJPARepository kvjpaRepository) {
        this.kvjpaRepository = kvjpaRepository;

        Optional<KVEntity> kvEntityOpt = kvjpaRepository.findById(KEY);
        if (kvEntityOpt.isPresent()) {
            KVEntity kvEntity = kvEntityOpt.get();

            entity = JSON.parseObject(kvEntity.getValue(), MQTTBrokerStateEntity.class);
        } else {
            save();
        }
    }

    @Override
    public synchronized void save(MQTTBrokerState mqttBrokerState) {
        entity = new MQTTBrokerStateEntity(mqttBrokerState.isStarted(),
                mqttBrokerState.isAllowAnonymousConnections(),
                mqttBrokerState.getPort().getValue());

        save();
    }

    @Override
    public synchronized MQTTBrokerState find() {
        return new MQTTBrokerState(
                PositiveNumber.of(entity.getPort()),
                entity.isAllowAnonymousConnections(),
                entity.isStarted()
        );
    }

    /*
     *
     *
     * */
    private void save() {
        KVEntity kvEntity = new KVEntity(KEY, JSON.toJSONString(entity));
        kvjpaRepository.save(kvEntity);
    }

}
