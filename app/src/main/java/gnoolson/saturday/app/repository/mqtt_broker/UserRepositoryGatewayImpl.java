package gnoolson.saturday.app.repository.mqtt_broker;

import gnoolson.saturday.app.repository.mqtt_broker.entity.UserEntity;
import gnoolson.saturday.app.utils.JSON;
import gnoolson.saturday.broker.model.User;
import gnoolson.saturday.broker.port.outbount.UserRepositoryGateway;
import gnoolson.saturday.common.model.vo.AuthPassword;
import gnoolson.saturday.common.model.vo.AuthUsername;
import org.apache.commons.io.FileUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRepositoryGatewayImpl implements UserRepositoryGateway {

    private final HashSet<UserEntity> users = new HashSet<>();
    private final File storageFile;
    private final BCryptPasswordEncoder encoder;

    /*
     *
     *
     * */
    public UserRepositoryGatewayImpl(File storageFile, BCryptPasswordEncoder encoder) {
        this.storageFile = storageFile;
        this.encoder = encoder;

        try {
            if (!storageFile.exists()) {
                storageFile.createNewFile();
                saveInFile();
            } else {
                String json = FileUtils.readFileToString(storageFile, StandardCharsets.UTF_8);
                HashSet<UserEntity> entities = new HashSet<>(JSON.parseArray(json, UserEntity.class));
                users.addAll(entities);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    public synchronized Set<User> findAll() {
        return users.stream().map(userEntity -> {
            return new User(
                    AuthUsername.of(userEntity.getUsername()),
                    AuthPassword.of(userEntity.getPassword())
            );
        }).collect(Collectors.toSet());
    }

    @Override
    public synchronized void save(User user) {
        String hash = encoder.encode(user.getPassword().getValue());
        users.removeIf(userEntity -> userEntity.getUsername().equals(user.getUsername().getValue()));
        users.add(new UserEntity(user.getUsername().getValue(), hash));
        saveInFile();
    }

    @Override
    public synchronized void delete(AuthUsername username) {
        users.removeIf((userEntity) -> {
            return userEntity.getUsername().equals(username.getValue());
        });

        saveInFile();
    }

    /*
     *
     *
     * */
    private void saveInFile() {
        try {
            FileUtils.writeStringToFile(storageFile, JSON.toJSONString(users), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
