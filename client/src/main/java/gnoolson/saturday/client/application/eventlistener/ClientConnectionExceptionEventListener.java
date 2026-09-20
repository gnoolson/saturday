package gnoolson.saturday.client.application.eventlistener;

import gnoolson.locker.Locker;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.model.exception.ClientNotFountException;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.common.eventbus.Callback;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ClientConnectionExceptionEvent;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.time.TimeProvider;
import gnoolson.saturday.common.transaction.TransactionStarter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ClientConnectionExceptionEventListener implements Callback<ClientConnectionExceptionEvent> {

    private final ClientRepositoryGateway clientRepositoryGateway;
    private final TimeProvider timeProvider;
    private final Locker locker;
    private final TransactionStarter transactionStarter;

    /*
     *
     *
     * */
    public ClientConnectionExceptionEventListener(EventBus eventBus, ClientRepositoryGateway clientRepositoryGateway, TimeProvider timeProvider, Locker locker, TransactionStarter transactionStarter) {
        this.clientRepositoryGateway = clientRepositoryGateway;
        this.timeProvider = timeProvider;
        this.locker = locker;
        this.transactionStarter = transactionStarter;
        eventBus.on(ClientConnectionExceptionEvent.class, this);
    }

    @Override
    public void exec(ClientConnectionExceptionEvent event) {
        ClientId clientId = event.getClientId();
        try (Locker.LockHandle ignore = locker.lockIds(clientId.toString())) {
            transactionStarter.doIt(() -> {
                Client client = clientRepositoryGateway.find(clientId).orElseThrow(() -> new ClientNotFountException(clientId));
                client.setConnectionErrorInformation(event.getException(), timeProvider.now());
                clientRepositoryGateway.save(client);
            });
        }
    }


}
