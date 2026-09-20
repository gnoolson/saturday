package gnoolson.saturday.broker.port.inbount;

import gnoolson.saturday.common.model.vo.AuthPassword;
import gnoolson.saturday.common.model.vo.AuthUsername;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface CreateUserUseCase {

    void execute(UserDto userDto);

    /*
     *
     *
     * */
    @Getter
    @RequiredArgsConstructor
    class UserDto {
        private final AuthUsername username;
        private final AuthPassword password;
    }

}
