package gnoolson.saturday.user.port.inbound;

import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.common.model.vo.Username;
import gnoolson.saturday.user.model.vo.Password;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


public interface GetUsersUseCase {

    List<UserDto> execute();

    /*
     *
     *
     * */
    @Getter
    @RequiredArgsConstructor
    class UserDto {
        private final Username username;
        private final Password password;
        private final Role role;
    }

}
