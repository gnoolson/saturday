package gnoolson.saturday.broker.port.inbount;

import gnoolson.saturday.common.model.vo.AuthUsername;

public interface DeleteUserUseCase {

    void execute(AuthUsername username);

}
