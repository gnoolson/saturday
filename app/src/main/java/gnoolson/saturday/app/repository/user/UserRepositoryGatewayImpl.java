package gnoolson.saturday.app.repository.user;

import gnoolson.saturday.app.repository.user.entity.UserEntity;
import gnoolson.saturday.app.utils.JSON;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.common.model.vo.Username;
import gnoolson.saturday.user.model.entity.User;
import gnoolson.saturday.user.model.vo.Password;
import gnoolson.saturday.user.port.outbound.UserRepositoryGateway;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public class UserRepositoryGatewayImpl implements UserRepositoryGateway {

    private final HashSet<UserEntity> users = new HashSet<>();
    private final File storageFile;

    /*
     *
     *
     * */
    public UserRepositoryGatewayImpl(File storageFile) {
        this.storageFile = storageFile;

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

    //    public static void main(String[] args) {
//        try {
//            UserRepositoryGatewayImpl userRepositoryGateway = new UserRepositoryGatewayImpl(ResourceUtils.getFile("file:./data/users"));
//            userRepositoryGateway.save(
//                    new User(Username.of("root"),
//                            Password.of("$2a$12$MtMNIzI6qYCTRjF8Jas7e.qikBFXRyZu929zd0Yo843tZyHrrZ7SS"),
//                            Role.valueOf("EDITOR")
//                            ));
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
    /*
     *
     *
     * */
    @Override
    public synchronized Optional<User> findByUsername(Username username) {
        for (UserEntity userEntity : users) {
            if (userEntity.getUsername().equals(username.getValue())) {
                return Optional.of(
                        new User(
                                Username.of(userEntity.getUsername()),
                                Password.of(userEntity.getPassword()),
                                Role.valueOf(userEntity.getRole())
                        )
                );
            }
        }

        return Optional.empty();
    }

    @Override
    public synchronized Set<User> findAll() {
        return users.stream().sorted(new Comparator<UserEntity>() {
            @Override
            public int compare(UserEntity o1, UserEntity o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
        }).map(userEntity -> {
            return new User(
                    Username.of(userEntity.getUsername()),
                    Password.of(userEntity.getPassword()),
                    Role.valueOf(userEntity.getRole())
            );
        }).collect(Collectors.toSet());
    }

    @Override
    public synchronized void save(User user) {
        users.removeIf((userEntity) -> {
            return userEntity.getUsername().equals(user.getUsername().getValue());
        });

        users.add(new UserEntity(user.getUsername().getValue(), user.getPassword().getValue(), user.getRole().name()));
        saveInFile();
    }

    @Override
    public synchronized void delete(Username username) {
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
