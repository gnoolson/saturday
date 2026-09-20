package gnoolson.saturday.user.port.inbound;

import gnoolson.saturday.common.model.vo.Username;

public interface DeleteUserUseCase {

    void execute(Username username, Username executor);

}
