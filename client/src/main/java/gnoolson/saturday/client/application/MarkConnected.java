package gnoolson.saturday.client.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.model.exception.ClientNotFountException;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.time.TimeProvider;
import gnoolson.saturday.common.transaction.TransactionStarter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

// +
@Log4j2
@RequiredArgsConstructor
public class MarkConnected {

    private final ClientRepositoryGateway clientRepositoryGateway;
    private final TimeProvider timeProvider;
    private final Locker locker;
    private final TransactionStarter transactionStarter;

    /*
     *
     *
     * */
    public void exec(ClientId clientId) {
        if (log.isDebugEnabled())
            log.debug("Client connected \"{}\"", clientId.getValue());

        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(clientId))) {
            transactionStarter.doIt(() -> {
                Client client = clientRepositoryGateway.find(clientId).orElseThrow(() -> new ClientNotFountException(clientId));
                client.markConnected(timeProvider.now());
                client.eraseConnectionError();
                clientRepositoryGateway.save(client);
            });
        }
    }

}
