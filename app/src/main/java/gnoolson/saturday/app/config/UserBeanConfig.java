package gnoolson.saturday.app.config;

import gnoolson.locker.Locker;
import gnoolson.saturday.app.repository.user.UserRepositoryGatewayImpl;
import gnoolson.saturday.user.application.*;
import gnoolson.saturday.user.port.inbound.*;
import gnoolson.saturday.user.port.outbound.GetAllAvailableLanguagesGateway;
import gnoolson.saturday.user.port.outbound.UserRepositoryGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

@Configuration
public class UserBeanConfig {

    @Bean
    public CreateFirstEditorUseCase userBean1(UserRepositoryGateway userRepositoryGateway, Locker locker) {
        return new CreateFirstEditorUseCaseImpl(userRepositoryGateway, locker);
    }

    @Bean
    public GetNumberOfUsersUseCase userBean2(UserRepositoryGateway userRepositoryGateway) {
        return new GetNumberOfUsersUseCaseImpl(userRepositoryGateway);
    }

    @Bean
    public GetAllAvailableLanguagesUseCase userBean3(GetAllAvailableLanguagesGateway getAllAvailableLanguagesGateway) {
        return new GetAllAvailableLanguagesUseCaseImpl(getAllAvailableLanguagesGateway);
    }

    @Bean
    public DeleteUserUseCase userBean4(UserRepositoryGateway userRepositoryGateway, Locker locker) {
        return new DeleteUserUseCaseImpl(userRepositoryGateway, locker);
    }

    @Bean
    public SetUserPasswordUseCase userBean5(UserRepositoryGateway userRepositoryGateway, Locker locker) {
        return new SetUserPasswordUseCaseImpl(userRepositoryGateway, locker);
    }

    @Bean
    public CreateUserUseCase userBean6(UserRepositoryGateway userRepositoryGateway, Locker locker) {
        return new CreateUserUseCaseImpl(userRepositoryGateway, locker);
    }

    @Bean
    public SetUserRoleUseCase userBean7(UserRepositoryGateway userRepositoryGateway, Locker locker) {
        return new SetUserRoleUseCaseImpl(userRepositoryGateway, locker);
    }

    @Bean
    public UserRepositoryGateway userBean8(@Value("${gnoolson.saturday.users.storage_file}") String storageFile) throws FileNotFoundException {
        return new UserRepositoryGatewayImpl(ResourceUtils.getFile(storageFile));
    }

    @Bean
    public GetUsersUseCase userBean9(UserRepositoryGateway userRepositoryGateway) {
        return new GetUsersUseCaseImpl(userRepositoryGateway);
    }

}
