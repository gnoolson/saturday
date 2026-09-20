package gnoolson.saturday.user.port.inbound;

import gnoolson.saturday.common.model.vo.Username;
import gnoolson.saturday.user.model.vo.Password;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface CreateFirstEditorUseCase {

    void execute(CreateUserDto createUserDto);

    @Getter
    @RequiredArgsConstructor
    class CreateUserDto {
        private final Username username;
        private final Password password;
    }

}
