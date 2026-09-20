package gnoolson.saturday.user.application;

import gnoolson.saturday.common.model.vo.PositiveNumber;
import gnoolson.saturday.user.port.inbound.GetNumberOfUsersUseCase;
import gnoolson.saturday.user.port.outbound.UserRepositoryGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetNumberOfUsersUseCaseImpl implements GetNumberOfUsersUseCase {

    private final UserRepositoryGateway userRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public PositiveNumber execute() {
        return PositiveNumber.of(userRepositoryGateway.findAll().size());
    }

}
