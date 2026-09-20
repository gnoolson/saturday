package gnoolson.saturday.app.config;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.eventbus.local.LocalEventBus;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.project.application.*;
import gnoolson.saturday.project.port.inbound.*;
import gnoolson.saturday.project.port.outbound.ProjectRepositoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectBeanConfig {

    @Bean
    public ImportProjectsUseCase projectBean1(ProjectRepositoryGateway projectRepositoryGateway) {
        return new ImportProjectsUseCaseImpl(projectRepositoryGateway);
    }

    @Bean
    public GetProjectsForExportUseCase projectBean2(ProjectRepositoryGateway projectRepositoryGateway) {
        return new GetProjectsForExportUseCaseImpl(projectRepositoryGateway);
    }

    @Bean
    public DeleteProjectUseCase projectBean3(ProjectRepositoryGateway projectRepositoryGateway,
                                             TransactionStarter transactionStarter,
                                             LocalEventBus localEventBus,
                                             Locker locker
    ) {

        return new DeleteProjectUseCaseImpl(projectRepositoryGateway, transactionStarter, localEventBus, locker);
    }

    @Bean
    public UpdateProjectUseCase projectBean4(TransactionStarter transactionStarter,
                                             Locker locker,
                                             ProjectRepositoryGateway projectRepositoryGateway) {

        return new UpdateProjectUseCaseImpl(transactionStarter, locker, projectRepositoryGateway);
    }

    @Bean
    public CreateProjectUseCase projectBean5(TransactionStarter transactionStarter,
                                             Locker locker,
                                             ProjectRepositoryGateway projectRepositoryGateway) {

        return new CreateProjectUseCaseImpl(transactionStarter, locker, projectRepositoryGateway);
    }

    @Bean
    public GetProjectUseCase projectBean6(ProjectRepositoryGateway projectRepositoryGateway) {
        return new GetProjectUseCaseImpl(projectRepositoryGateway);
    }

    @Bean
    public GetAllProjectsUseCase projectBean7(ProjectRepositoryGateway projectRepositoryGateway) {
        return new GetAllProjectsUseCaseImpl(projectRepositoryGateway);
    }

}
