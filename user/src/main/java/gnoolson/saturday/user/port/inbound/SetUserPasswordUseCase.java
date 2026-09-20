package gnoolson.saturday.user.port.inbound;

import gnoolson.saturday.common.model.vo.Username;
import gnoolson.saturday.user.model.vo.Password;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface SetUserPasswordUseCase {

    void execute(SetUserPasswordDto setUserPasswordDto, Username executor);

    /*
     *
     *
     * */
    @Getter
    @RequiredArgsConstructor
    class SetUserPasswordDto {
        private final Username username;
        private final Password password;
    }

}
