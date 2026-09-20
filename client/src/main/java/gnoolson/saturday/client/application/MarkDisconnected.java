package gnoolson.saturday.client.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.time.TimeProvider;
import gnoolson.saturday.common.transaction.TransactionStarter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

// +
@Log4j2
@RequiredArgsConstructor
public class MarkDisconnected {

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
            log.debug("Client disconnected \"{}\"", clientId.getValue());

        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(clientId))) {
            transactionStarter.doIt(() -> {
                Optional<Client> clientOpt = clientRepositoryGateway.find(clientId);
                if (!clientOpt.isPresent()) {
                    log.debug("Client \"{}\" was not found. Maybe it was deleted.", clientId.getValue().toString());
                    return;
                }
                Client client = clientOpt.get();
                client.markDisconnected(timeProvider.now());
                client.eraseConnectionError();
                clientRepositoryGateway.save(client);
            });
        }
    }

}
