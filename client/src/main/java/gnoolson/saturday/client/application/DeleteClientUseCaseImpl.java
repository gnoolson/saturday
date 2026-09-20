package gnoolson.saturday.client.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.client.model.exception.ClientNotFountException;
import gnoolson.saturday.client.port.inbound.DeleteClientUseCase;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class DeleteClientUseCaseImpl implements DeleteClientUseCase {

    private final Locker locker;
    private final ClientRepositoryGateway clientRepositoryGateway;
    private final TransactionStarter transactionStarter;

    /*
     *
     *
     * */
    @Override
    public boolean execute(ClientId clientId) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(clientId))) {
            transactionStarter.doIt(() -> {
                clientRepositoryGateway.find(clientId).orElseThrow(() -> new ClientNotFountException(clientId));
                clientRepositoryGateway.delete(clientId);
            });

            return true;
        } catch (Exception e) {
            log.info("Exception", e);
            return false;
        }
    }

}
