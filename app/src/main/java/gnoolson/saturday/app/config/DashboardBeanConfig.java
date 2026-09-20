package gnoolson.saturday.app.config;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.time.TimeProvider;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.dashboard.application.*;
import gnoolson.saturday.dashboard.model.vo.Html;
import gnoolson.saturday.dashboard.port.inbound.*;
import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
import gnoolson.saturday.dashboard.port.outbound.DashboardScriptLauncherGateway;
import gnoolson.saturday.dashboard.port.outbound.ProjectIdCheckerGateway;
import gnoolson.saturday.dashboard.port.outbound.ScriptIdCheckerGateway;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class DashboardBeanConfig {

    @Bean
    public GetOpenDashboardsUseCase dashboardBean(OpenDashboardsService openDashboardsService) {
        return new GetOpenDashboardsInProjectUseCaseImpl(openDashboardsService);
    }

    @Bean
    public RegistrationOpenDashboardUseCase dashboardBean1(OpenDashboardsService openDashboardsService) {
        return new RegistrationOpenDashboardUseCaseImpl(openDashboardsService);
    }

    @Bean
    public OpenDashboardsService dashboardBean2(TimeProvider timeProvider,
                                                Locker locker,
                                                DashboardRepositoryGateway dashboardRepositoryGateway) {

        return new OpenDashboardsServiceImpl(timeProvider, locker, dashboardRepositoryGateway);
    }

    @Bean
    public GetAllDashboardsForRootPageUseCase dashboardBean3(DashboardRepositoryGateway dashboardRepositoryGateway) {
        return new GetAllDashboardsForRootPageUseCaseImpl(dashboardRepositoryGateway);
    }

    @Bean
    public ImportDashboardsUseCase dashboardBean4(DashboardRepositoryGateway dashboardRepositoryGateway,
                                                  ScriptIdCheckerGateway scriptIdCheckerGateway) {

        return new ImportDashboardsUseCaseImpl(dashboardRepositoryGateway, scriptIdCheckerGateway);
    }

    @Bean
    public GetDashboardsForExportUseCase dashboardBean5(DashboardRepositoryGateway dashboardRepositoryGateway) {
        return new GetDashboardsForExportUseCaseImpl(dashboardRepositoryGateway);
    }

    @Bean
    public GetDashboardNameUseCase dashboardBean6(DashboardRepositoryGateway dashboardRepositoryGateway) {
        return new GetDashboardNameUseCaseImpl(dashboardRepositoryGateway);
    }

    @Bean
    public CheckAuthUseCase dashboardBean7(DashboardRepositoryGateway dashboardRepositoryGateway) {
        return new CheckAuthUseCaseImpl(dashboardRepositoryGateway);
    }

    @Bean
    public UpdateHtmlUseCase dashboardBean8(DashboardRepositoryGateway dashboardRepositoryGateway,
                                            Locker locker,
                                            TransactionStarter transactionStarter) {

        return new UpdateHtmlUseCaseImpl(transactionStarter, locker, dashboardRepositoryGateway);
    }

    @Bean
    public DeleteDashboardUseCase dashboardBean9(DashboardRepositoryGateway dashboardRepositoryGateway,
                                                 Locker locker,
                                                 TransactionStarter transactionStarter) {

        return new DeleteDashboardUseCaseImpl(dashboardRepositoryGateway, transactionStarter, locker);
    }

    @Bean
    public UpdateDashboardUseCase dashboardBean10(DashboardRepositoryGateway dashboardRepositoryGateway,
                                                  Locker locker,
                                                  TransactionStarter transactionStarter,
                                                  ScriptIdCheckerGateway scriptIdCheckerGateway) {

        return new UpdateDashboardUseCaseImpl(transactionStarter, locker, dashboardRepositoryGateway, scriptIdCheckerGateway);
    }

    @Bean
    public GetDashboardUseCase dashboardBean11(DashboardRepositoryGateway dashboardRepositoryGateway) {
        return new GetDashboardUseCaseImpl(dashboardRepositoryGateway);
    }

    @Bean
    public GetDefaultHtmlTemplateUseCase dashboardBean12(@Value("${gnoolson.saturday.dashboard.template}") String path) throws IOException {
        File file = ResourceUtils.getFile(path);
        String html = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        return new GetDefaultHtmlTemplateUseCaseImpl(Html.of(html));
    }

    @Bean
    public CreateDashboardUseCase dashboardBean13(DashboardRepositoryGateway dashboardRepositoryGateway,
                                                  Locker locker,
                                                  TransactionStarter transactionStarter,
                                                  ScriptIdCheckerGateway scriptIdCheckerGateway,
                                                  ProjectIdCheckerGateway projectIdCheckerGateway) {

        return new CreateDashboardUseCaseImpl(dashboardRepositoryGateway, transactionStarter, locker, scriptIdCheckerGateway, projectIdCheckerGateway);
    }

    @Bean
    public GetAllDashboardsUseCase dashboardBean14(DashboardRepositoryGateway dashboardRepositoryGateway) {
        return new GetAllDashboardsUseCaseImpl(dashboardRepositoryGateway);
    }

    @Bean
    public GetDashboardHtmlUseCase dashboardBean15(DashboardRepositoryGateway dashboardRepositoryGateway, CheckAuthUseCase checkAuthUseCase) {
        return new GetDashboardHtmlUseCaseImpl(dashboardRepositoryGateway, checkAuthUseCase);
    }

    @Bean
    public gnoolson.saturday.dashboard.port.inbound.PostDashboardDataUseCase dashboardBean16(DashboardRepositoryGateway dashboardRepositoryGateway,
                                                                                             DashboardScriptLauncherGateway dashboardScriptLauncherGateway,
                                                                                             CheckAuthUseCase checkAuthUseCase) {

        return new PostDashboardDataUseCaseImpl(dashboardRepositoryGateway, dashboardScriptLauncherGateway, checkAuthUseCase);
    }

    @Bean
    public RequestTimeoutUseCaseImpl dashboardBean17(OpenDashboardsService openDashboardsService) {
        return new RequestTimeoutUseCaseImpl(openDashboardsService);
    }

}
