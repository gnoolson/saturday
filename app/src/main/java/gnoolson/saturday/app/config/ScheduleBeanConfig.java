package gnoolson.saturday.app.config;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.schedule.application.*;
import gnoolson.saturday.schedule.port.inbound.*;
import gnoolson.saturday.schedule.port.outbound.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduleBeanConfig {

    @Bean
    public ImportSchedulesUseCase scheduleBean1(ScheduleRepositoryGateway scheduleRepositoryGateway,
                                                ScriptIdCheckerGateway scriptIdCheckerGateway,
                                                EventBus eventBus,
                                                TransactionStarter transactionStarter) {

        return new ImportSchedulesUseCaseImpl(scheduleRepositoryGateway, scriptIdCheckerGateway, eventBus, transactionStarter);
    }

    @Bean
    public GetSchedulesForExportUseCase scheduleBean2(ScheduleRepositoryGateway scheduleRepositoryGateway) {
        return new GetSchedulesForExportUseCaseImpl(scheduleRepositoryGateway);
    }

    @Bean
    public DeleteScheduleUseCase scheduleBean3(TransactionStarter transactionStarter, Locker locker, ScheduleRepositoryGateway scheduleRepositoryGateway, EventBus eventBus) {
        return new DeleteScheduleUseCaseImpl(transactionStarter, locker, scheduleRepositoryGateway, eventBus);
    }

    @Bean
    public ScheduleControlUseCase scheduleBean4(TransactionStarter transactionStarter, Locker locker, ScheduleRepositoryGateway scheduleRepositoryGateway, EventBus eventBus) {
        return new ScheduleControlUseCaseImpl(transactionStarter, locker, scheduleRepositoryGateway, eventBus);
    }

    @Bean
    public GetScheduleUseCase scheduleBean5(ScheduleRepositoryGateway scheduleRepositoryGateway) {
        return new GetScheduleUseCaseImpl(scheduleRepositoryGateway);
    }

    @Bean
    public UpdateScheduleUseCase scheduleBean6(TransactionStarter transactionStarter, Locker locker, ScheduleRepositoryGateway scheduleRepositoryGateway, EventBus eventBus, ScriptIdCheckerGateway scriptIdCheckerGateway) {
        return new UpdateScheduleUseCaseImpl(transactionStarter, locker, scheduleRepositoryGateway, eventBus, scriptIdCheckerGateway);
    }

    @Bean
    public UpdateScheduleEventListener scheduleBean7(EventBus eventBus, ScheduleManager scheduleManager) {
        return new UpdateScheduleEventListener(eventBus, scheduleManager);
    }

    @Bean
    public CreateScheduleUseCase scheduleBean8(TransactionStarter transactionStarter,
                                               Locker locker,
                                               ScheduleRepositoryGateway scheduleRepositoryGateway,
                                               ScriptIdCheckerGateway scriptIdCheckerGateway,
                                               ProjectIdCheckerGateway projectIdCheckerGateway) {

        return new CreateScheduleUseCaseImpl(transactionStarter, locker, scheduleRepositoryGateway, scriptIdCheckerGateway, projectIdCheckerGateway);
    }

    @Bean
    public ScheduleManager scheduleBean9(ScheduleStarterGateway scheduleStarterGateway,
                                         ScheduledFutureRepositor scheduledFutureRepositor,
                                         ScheduleScriptLauncherGateway scheduleScriptLauncherGateway) {

        return new ScheduleManager(scheduleStarterGateway, scheduledFutureRepositor, scheduleScriptLauncherGateway);
    }

    @Bean
    public ScheduledFutureRepositor scheduleBean10() {
        return new ScheduledFutureRepositor();
    }

    @Bean
    public RefreshSchedulesUseCase scheduleBean11(ScheduleRepositoryGateway scheduleRepositoryGateway, ScheduleManager scheduleManager) {
        return new RefreshSchedulesUseCaseImpl(scheduleRepositoryGateway, scheduleManager);
    }

    @Bean
    public GetAllSchedulesUseCase scheduleBean12(ScheduleRepositoryGateway scheduleRepositoryGateway) {
        return new GetAllSchedulesUseCaseImpl(scheduleRepositoryGateway);
    }

}
