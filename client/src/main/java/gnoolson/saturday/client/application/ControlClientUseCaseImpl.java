package gnoolson.saturday.client.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.model.exception.ClientNotFountException;
import gnoolson.saturday.client.port.inbound.ControlClientUseCase;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ControlClientUseCaseImpl implements ControlClientUseCase {

    private final Locker locker;
    private final ClientRepositoryGateway clientRepositoryGateway;
    private final TransactionStarter transactionStarter;

    /*
     *
     *
     * */
    @Override
    public void execute(ClientId id, boolean enable) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(id))) {
            transactionStarter.doIt(() -> {
                Optional<Client> clientOptional = clientRepositoryGateway.find(id);
                Client client = clientOptional.orElseThrow(() -> new ClientNotFountException(id));
                if (enable)
                    client.enable();
                else
                    client.disable();

                clientRepositoryGateway.save(client);
            });
        }
    }

}
