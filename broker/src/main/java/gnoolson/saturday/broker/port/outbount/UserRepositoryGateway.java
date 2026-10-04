package gnoolson.saturday.broker.port.outbount;

import gnoolson.saturday.broker.model.User;
import gnoolson.saturday.common.model.vo.AuthPassword;
import gnoolson.saturday.common.model.vo.AuthUsername;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepositoryGateway {

    Optional<User> find(AuthUsername username);

    boolean exists(AuthUsername username, AuthPassword password);

    Set<User> findAll();

    void save(User user);

    void delete(AuthUsername username);

}
