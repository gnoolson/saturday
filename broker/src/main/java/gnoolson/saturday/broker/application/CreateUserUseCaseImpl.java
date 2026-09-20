package gnoolson.saturday.broker.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.broker.model.User;
import gnoolson.saturday.broker.port.inbount.CreateUserUseCase;
import gnoolson.saturday.broker.port.outbount.UserRepositoryGateway;
import gnoolson.saturday.common.locker.LockId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepositoryGateway userRepositoryGateway;
    private final Locker locker;

    /*
     *
     *
     * */
    @Override
    public void execute(UserDto userDto) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(userDto.getUsername()))) {
            checkUsername(userDto);
            userRepositoryGateway.save(new User(userDto.getUsername(), userDto.getPassword()));
        }
    }

    /*
     *
     *
     * */
    private void checkUsername(UserDto userDto) {
        if (userRepositoryGateway.find(userDto.getUsername()).isPresent())
            throw new IllegalStateException(String.format("User \"%s\" already exists", userDto.getUsername().getValue())); // +
    }

}
