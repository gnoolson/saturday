package gnoolson.saturday.broker.port.inbount;

import gnoolson.saturday.common.model.vo.AuthUsername;
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
        private final AuthUsername username;
    }

}
