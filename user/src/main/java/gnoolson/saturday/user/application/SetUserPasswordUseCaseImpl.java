package gnoolson.saturday.user.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.common.model.vo.Username;
import gnoolson.saturday.user.model.entity.User;
import gnoolson.saturday.user.port.inbound.SetUserPasswordUseCase;
import gnoolson.saturday.user.port.outbound.UserRepositoryGateway;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class SetUserPasswordUseCaseImpl implements SetUserPasswordUseCase {

    private final UserRepositoryGateway userRepositoryGateway;
    private final Locker locker;

    /*
     *
     *
     * */
    @Override
    public void execute(SetUserPasswordDto setUserPasswordDto, Username executor) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(setUserPasswordDto.getUsername()))) {

            User executorUser = userRepositoryGateway.findByUsername(executor).orElseThrow(() -> {
                return new RuntimeException(String.format("User \"%s\" was not found", executor.getValue())); // +
            });

            User user = userRepositoryGateway.findByUsername(setUserPasswordDto.getUsername()).orElseThrow(() -> {
                return new RuntimeException(String.format("User \"%s\" was not found", setUserPasswordDto.getUsername().getValue())); // +
            });

            if (areYourEditor(executorUser.getRole())) {
                user.setPassword(setUserPasswordDto.getPassword());
            } else if (isItSelfPassword(user.getUsername(), executor)) {
                user.setPassword(setUserPasswordDto.getPassword());
            } else {
                throw new RuntimeException("User with role 'VIEWER' cannot change another user's password"); // +
            }

            userRepositoryGateway.save(user);
        }
    }

    /*
     *
     *
     * */
    private boolean isItSelfPassword(Username username, Username executor) {
        return username.equals(executor);
    }

    private boolean areYourEditor(Role role) {
        return role == Role.EDITOR;
    }

}
