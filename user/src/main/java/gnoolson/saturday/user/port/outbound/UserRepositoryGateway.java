package gnoolson.saturday.user.port.outbound;


import gnoolson.saturday.common.model.vo.Username;
import gnoolson.saturday.user.model.entity.User;

import java.util.Optional;
import java.util.Set;

public interface UserRepositoryGateway {

    Optional<User> findByUsername(Username username);

    Set<User> findAll();

    void save(User user);

    void delete(Username username);

}
