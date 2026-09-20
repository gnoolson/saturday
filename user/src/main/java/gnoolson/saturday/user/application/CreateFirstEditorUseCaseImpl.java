package gnoolson.saturday.user.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.user.model.entity.User;
import gnoolson.saturday.user.port.inbound.CreateFirstEditorUseCase;
import gnoolson.saturday.user.port.outbound.UserRepositoryGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateFirstEditorUseCaseImpl implements CreateFirstEditorUseCase {

    private final UserRepositoryGateway userRepositoryGateway;
    private final Locker locker;

    /*
     *
     *
     * */
    @Override
    public void execute(CreateUserDto createUserDto) {
        try (Locker.LockHandle ignore = locker.lockIds()) {

            if (!userRepositoryGateway.findAll().isEmpty())
                throw new IllegalStateException("Editor already exists"); // +

            userRepositoryGateway.save(new User(
                    createUserDto.getUsername(),
                    createUserDto.getPassword(),
                    Role.EDITOR));
        }
    }

}
