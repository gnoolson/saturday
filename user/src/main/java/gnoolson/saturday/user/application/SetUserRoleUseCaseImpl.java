package gnoolson.saturday.user.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.Username;
import gnoolson.saturday.user.model.entity.User;
import gnoolson.saturday.user.port.inbound.SetUserRoleUseCase;
import gnoolson.saturday.user.port.outbound.UserRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class SetUserRoleUseCaseImpl implements SetUserRoleUseCase {

    private final UserRepositoryGateway userRepositoryGateway;
    private final Locker locker;

    /*
     *
     *
     * */
    @Override
    public void execute(SetUserRoleDto setUserRoleDto, Username executor) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(setUserRoleDto.getUsername()))) {
            checkAccess(setUserRoleDto.getUsername(), executor);

            Optional<User> userOpt = userRepositoryGateway.findByUsername(setUserRoleDto.getUsername());
            User user = userOpt.orElseThrow(() -> {
                return new RuntimeException(String.format("User \"%s\" was not found", setUserRoleDto.getUsername().getValue())); // +
            });

            user.setRole(gnoolson.saturday.common.model.vo.Role.valueOf(setUserRoleDto.getRole().name()));

            userRepositoryGateway.save(user);
        }
    }

    /*
     *
     *
     * */
    private void checkAccess(Username username, Username executor) {
        if (username.equals(executor))
            throw new IllegalStateException("User cannot change own role"); // +
    }

}
