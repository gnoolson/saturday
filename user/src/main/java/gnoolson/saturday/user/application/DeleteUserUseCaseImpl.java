package gnoolson.saturday.user.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.common.model.vo.Username;
import gnoolson.saturday.user.model.entity.User;
import gnoolson.saturday.user.port.inbound.DeleteUserUseCase;
import gnoolson.saturday.user.port.outbound.UserRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserRepositoryGateway userRepositoryGateway;
    private final Locker locker;

    /*
     *
     *
     * */
    @Override
    public void execute(Username username, Username executor) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(username))) {

            Optional<User> executorUserOpt = userRepositoryGateway.findByUsername(executor);
            User executorUser = executorUserOpt.orElseThrow(() -> {
                return new RuntimeException(String.format("User \"%s\" was not found", executor)); // +
            });

            Optional<User> userOpt = userRepositoryGateway.findByUsername(username);
            User user = userOpt.orElseThrow(() -> {
                return new RuntimeException(String.format("User \"%s\" was not found", username.getValue())); // +
            });

            if (executorUser.getRole().equals(Role.EDITOR) && !user.getUsername().equals(executor)) {
                userRepositoryGateway.delete(username);
            } else {
                throw new IllegalStateException("User with role 'VIEWER' cannot change another user's password"); // +
            }
        }
    }

}
