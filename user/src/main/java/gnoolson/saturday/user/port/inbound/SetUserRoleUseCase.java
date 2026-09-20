package gnoolson.saturday.user.port.inbound;

import gnoolson.saturday.common.model.vo.Username;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface SetUserRoleUseCase {

    void execute(SetUserRoleDto setUserRoleDto, Username executor);

    enum Role {
        EDITOR,
        VIEWER,
    }

    /*
     *
     *
     * */
    @Getter
    @RequiredArgsConstructor
    class SetUserRoleDto {
        private final Username username;
        private final Role role;
    }

}
