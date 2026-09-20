package gnoolson.saturday.user.application;

import gnoolson.saturday.user.model.entity.User;
import gnoolson.saturday.user.port.inbound.GetUsersUseCase;
import gnoolson.saturday.user.port.outbound.UserRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetUsersUseCaseImpl implements GetUsersUseCase {

    private final UserRepositoryGateway userRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public List<UserDto> execute() {
        return userRepositoryGateway.findAll().stream().map(new Function<User, UserDto>() {
            @Override
            public UserDto apply(User user) {
                return new UserDto(user.getUsername(), user.getPassword(), user.getRole());
            }
        }).collect(Collectors.toList());
    }

}
