package gnoolson.saturday.app.repository.mqtt_broker;

import gnoolson.saturday.app.repository.key_value.KVJPARepository;
import gnoolson.saturday.app.repository.key_value.entity.KVEntity;
import gnoolson.saturday.app.repository.mqtt_broker.entity.UserEntity;
import gnoolson.saturday.app.utils.JSON;
import gnoolson.saturday.broker.model.User;
import gnoolson.saturday.broker.port.outbount.UserRepositoryGateway;
import gnoolson.saturday.common.model.vo.AuthPassword;
import gnoolson.saturday.common.model.vo.AuthUsername;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRepositoryGatewayImpl implements UserRepositoryGateway {

    private final Set<UserEntity> users = new HashSet<>();
    private final BCryptPasswordEncoder encoder;
    private final String KEY = "MQTT_USERS";
    private final KVJPARepository kvjpaRepository;

    /*
     *
     *
     * */
    public UserRepositoryGatewayImpl(KVJPARepository kvjpaRepository, BCryptPasswordEncoder encoder) {
        this.kvjpaRepository = kvjpaRepository;
        this.encoder = encoder;
        load();
    }

    @Override
    public synchronized Optional<User> find(AuthUsername username) {
        for (UserEntity userEntity : users) {
            if (userEntity.getUsername().equals(username.getValue())) {
                return Optional.of(
                        new User(
                                AuthUsername.of(userEntity.getUsername()),
                                AuthPassword.of(userEntity.getPassword())
                        )
                );
            }
        }

        return Optional.empty();
    }

    @Override
    public synchronized boolean exists(AuthUsername username, AuthPassword password) {
        Optional<User> userOpt = find(username);
        if (!userOpt.isPresent())
            return false;

        return encoder.matches(password.getValue(), userOpt.get().getPassword().getValue());
    }

    @Override
    public synchronized List<User> findAll() {
        return users.stream().map(
                entry ->
                        new User(AuthUsername.of(entry.getUsername()), AuthPassword.empty())
        ).collect(Collectors.toList());
    }

    @Override
    public synchronized void save(User user) {
        String hash = encoder.encode(user.getPassword().getValue());
        users.removeIf(userEntity -> userEntity.getUsername().equals(user.getUsername().getValue()));
        users.add(new UserEntity(user.getUsername().getValue(), hash));
        save();
    }

    @Override
    public synchronized void delete(AuthUsername username) {
        users.removeIf(userEntity -> userEntity.getUsername().equals(username.getValue()));
        save();
    }

    /*
     *
     *
     * */
    private void load() {
        Optional<KVEntity> kvEntityOpt = kvjpaRepository.findById(KEY);
        if (kvEntityOpt.isPresent()) {
            KVEntity kvEntity = kvEntityOpt.get();

            List<UserEntity> entities = JSON.parseArray(kvEntity.getValue(), UserEntity.class);
            users.addAll(entities);
        } else {
            save();
        }
    }

    private void save() {
        KVEntity kvEntity = new KVEntity(KEY, JSON.toJSONString(users));
        kvjpaRepository.save(kvEntity);
    }

}
