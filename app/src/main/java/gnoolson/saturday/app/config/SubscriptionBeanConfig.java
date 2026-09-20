package gnoolson.saturday.app.config;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.eventbus.local.LocalEventBus;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.subscription.application.*;
import gnoolson.saturday.subscription.port.inbound.*;
import gnoolson.saturday.subscription.port.outbound.GetClientProjectIdGateway;
import gnoolson.saturday.subscription.port.outbound.ScriptIdCheckerGateway;
import gnoolson.saturday.subscription.port.outbound.SubscriptionRepositoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriptionBeanConfig {

    @Bean
    public ImportSubscriptionsUseCase subscriptionBean1(ScriptIdCheckerGateway scriptIdCheckerGateway,
                                                        SubscriptionRepositoryGateway subscriptionRepositoryGateway,
                                                        TransactionStarter transactionStarter,
                                                        LocalEventBus localEventBus,
                                                        GetClientProjectIdGateway getClientProjectIdGateway) {

        return new ImportSubscriptionsUseCaseImpl(scriptIdCheckerGateway, subscriptionRepositoryGateway, transactionStarter, localEventBus, getClientProjectIdGateway);
    }

    @Bean
    public GetSubscriptionsForExportUseCase subscriptionBean2(SubscriptionRepositoryGateway subscriptionRepositoryGateway) {
        return new GetSubscriptionsForExportUseCaseImpl(subscriptionRepositoryGateway);
    }

    @Bean
    public DeleteSubscriptionUseCase subscriptionBean3(TransactionStarter transactionStarter,
                                                       Locker locker,
                                                       SubscriptionRepositoryGateway subscriptionRepositoryGateway,
                                                       LocalEventBus localEventBus) {

        return new DeleteSubscriptionUseCaseImpl(transactionStarter, locker, subscriptionRepositoryGateway, localEventBus);
    }

    @Bean
    public UpdateSubscriptionUseCase subscriptionBean4(SubscriptionRepositoryGateway subscriptionRepositoryGateway,
                                                       LocalEventBus localEventBus,
                                                       TransactionStarter transactionStarter,
                                                       Locker locker) {

        return new UpdateSubscriptionUseCaseImpl(subscriptionRepositoryGateway, localEventBus, transactionStarter, locker);
    }

    @Bean
    public GetSubscriptionUseCase subscriptionBean5(SubscriptionRepositoryGateway subscriptionRepositoryGateway) {
        return new GetSubscriptionUseCaseImpl(subscriptionRepositoryGateway);
    }

    @Bean
    public GetAllSubscriptionsUseCase subscriptionBean6(SubscriptionRepositoryGateway subscriptionRepositoryGateway) {
        return new GetAllSubscriptionsUseCaseImpl(subscriptionRepositoryGateway);
    }

    @Bean
    public CreateSubscriptionUseCase subscriptionBean7(Locker locker,
                                                       TransactionStarter transactionStarter,
                                                       ScriptIdCheckerGateway scriptIdCheckerGateway,
                                                       SubscriptionRepositoryGateway subscriptionRepositoryGateway,
                                                       LocalEventBus localEventBus,
                                                       GetClientProjectIdGateway getClientProjectIdGateway) {

        return new CreateSubscriptionUseCaseImpl(locker, transactionStarter, scriptIdCheckerGateway, subscriptionRepositoryGateway, localEventBus, getClientProjectIdGateway);
    }

}
