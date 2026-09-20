package gnoolson.saturday.broker.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.broker.port.inbount.DeleteUserUseCase;
import gnoolson.saturday.broker.port.outbount.UserRepositoryGateway;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.AuthUsername;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserRepositoryGateway userRepositoryGateway;
    private final Locker locker;


    @Override
    public void execute(AuthUsername username) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(username))) {
            checkUsername(username);
            userRepositoryGateway.delete(username);
        }
    }

    /*
     *
     *
     * */
    private void checkUsername(AuthUsername username) {
        if (!userRepositoryGateway.find(username).isPresent())
            throw new RuntimeException(String.format("User \"%s\" was not found", username.getValue())); // +
    }

}
