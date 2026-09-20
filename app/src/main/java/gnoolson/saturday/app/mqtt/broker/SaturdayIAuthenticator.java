package gnoolson.saturday.app.mqtt.broker;

import gnoolson.saturday.broker.port.outbount.UserRepositoryGateway;
import gnoolson.saturday.common.model.vo.AuthPassword;
import gnoolson.saturday.common.model.vo.AuthUsername;
import io.moquette.broker.security.IAuthenticator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaturdayIAuthenticator implements IAuthenticator {

    private final UserRepositoryGateway userRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public synchronized boolean checkValid(String clientId, String username, byte[] password) {
        if (username == null || password == null)
            return false;

        return userRepositoryGateway.exists(AuthUsername.of(username), AuthPassword.of(new String(password)));
    }

}