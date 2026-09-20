package gnoolson.saturday.user.port.inbound;

import gnoolson.saturday.common.model.vo.Username;
import gnoolson.saturday.user.model.vo.Password;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface CreateUserUseCase {

    void execute(CreateUserDto createUserDto);

    enum Role {
        EDITOR,
        VIEWER
    }

    @Getter
    @RequiredArgsConstructor
    class CreateUserDto {
        private final Username username;
        private final Password password;
        private final Role role;
    }

}
