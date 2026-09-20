package gnoolson.saturday.client;

import gnoolson.locker.Locker;
import gnoolson.locker.OptimisticLocalLocker;
import gnoolson.saturday.client.application.MqttClient;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.port.outbound.CreateMQTTClientGateway;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.local.LocalEventBus;
import gnoolson.saturday.common.transaction.*;

public class GeneralTest {

    protected TransactionStarter transactionStarter = new TransactionStarter(new TransactionSynchronizationManagerWrapper() {
        @Override
        public void register(TransactionSynchronizationAdapterWrapper wrapper) {
            wrapper.exec();
        }
    }, new TransactionExecutor() {
        @Override
        public void exec(TransactionBody transactionBody) {
            transactionBody.exec();
        }
    });

    protected Locker locker = new OptimisticLocalLocker();

    protected EventBus eventBus = new LocalEventBus();

    protected CreateMQTTClientGateway createMqttClientGateway = new CreateMQTTClientGateway() {
        @Override
        public MqttClient execute(Client client) {
            return null;
        }
    };

}
