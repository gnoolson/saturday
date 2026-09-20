package gnoolson.saturday.user.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.user.model.entity.User;
import gnoolson.saturday.user.port.inbound.CreateUserUseCase;
import gnoolson.saturday.user.port.outbound.UserRepositoryGateway;
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
    public void execute(CreateUserDto createUserDto) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(createUserDto.getUsername()))) {
            checkUsername(createUserDto);

            userRepositoryGateway.save(new User(
                    createUserDto.getUsername(),
                    createUserDto.getPassword(),
                    gnoolson.saturday.common.model.vo.Role.valueOf(createUserDto.getRole().name())));
        }
    }

    /*
     *
     *
     * */
    private void checkUsername(CreateUserDto createUserDto) {
        if (userRepositoryGateway.findByUsername(createUserDto.getUsername()).isPresent())
            throw new RuntimeException(String.format("User \"%s\" already exists", createUserDto.getUsername().getValue())); // +
    }

}
