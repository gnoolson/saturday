package gnoolson.saturday.broker.application;

import gnoolson.saturday.broker.port.inbount.GetUsersUseCase;
import gnoolson.saturday.broker.port.outbount.UserRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;
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
        return userRepositoryGateway.findAll().stream().map(user -> new UserDto(user.getUsername())).collect(Collectors.toList());
    }

}
