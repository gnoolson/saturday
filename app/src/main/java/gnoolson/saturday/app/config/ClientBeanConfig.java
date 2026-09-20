package gnoolson.saturday.app.config;

import gnoolson.locker.Locker;
import gnoolson.saturday.client.application.*;
import gnoolson.saturday.client.application.eventlistener.*;
import gnoolson.saturday.client.port.inbound.*;
import gnoolson.saturday.client.port.outbound.*;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.time.TimeProvider;
import gnoolson.saturday.common.transaction.TransactionStarter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ClientBeanConfig {

    @Bean
    public ImportClientsUseCase clientBean1(ClientRepositoryGateway clientRepositoryGateway, ScriptIdCheckerGateway scriptIdCheckerGateway) {
        return new ImportClientsUseCaseImpl(clientRepositoryGateway, scriptIdCheckerGateway);
    }

    @Bean
    public GetClientsForExportUseCase clientBean2(ClientRepositoryGateway clientRepositoryGateway) {
        return new GetClientsDataForExportUseCaseImpl(clientRepositoryGateway);
    }

    @Bean
    public ScriptUpdatedEventListener clientBean3(EventBus eventBus, ClientSubscriptionUpdater clientSubscriptionUpdater) {
        return new ScriptUpdatedEventListener(eventBus, clientSubscriptionUpdater);
    }

    @Bean
    public ClientSubscriptionUpdatedEventListener clientBean4(EventBus eventBus, ClientSubscriptionUpdater clientSubscriptionUpdater) {
        return new ClientSubscriptionUpdatedEventListener(eventBus, clientSubscriptionUpdater);
    }

    @Bean
    public ClientConnectedEventHandler clientBean5(ClientRepositoryGateway clientRepositoryGateway,
                                                   ClientConnectedScriptLauncherGateway clientConnectedScriptLauncherGateway,
                                                   ScriptIdCheckerGateway scriptIdCheckerGateway) {

        return new ClientConnectedEventHandler(clientRepositoryGateway, clientConnectedScriptLauncherGateway, scriptIdCheckerGateway);
    }

    @Bean
    public ClientDisconnectedEventHandler clientBean6(ClientRepositoryGateway clientRepositoryGateway,
                                                      ClientDisconnectedScriptLauncherGateway clientDisconnectedScriptLauncherGateway,
                                                      ScriptIdCheckerGateway scriptIdCheckerGateway) {

        return new ClientDisconnectedEventHandler(clientRepositoryGateway, clientDisconnectedScriptLauncherGateway, scriptIdCheckerGateway);
    }

    @Bean
    public ClientSubscriptionUpdater clientBean7(MQTTClientManager mqttClientManager,
                                                 ScriptLauncherGateway scriptLauncherGateway,
                                                 SubscriptionRepositoryGateway subscriptionRepositoryGateway,
                                                 Locker locker, ClientRepositoryGateway clientRepositoryGateway) {

        return new ClientSubscriptionUpdater(mqttClientManager,
                scriptLauncherGateway,
                subscriptionRepositoryGateway,
                locker,
                clientRepositoryGateway);
    }

    @Bean
    public CreateClientUseCase clientBean8(ClientRepositoryGateway clientRepositoryGateway,
                                           Locker locker,
                                           TransactionStarter transactionStarter,
                                           ScriptIdCheckerGateway scriptIdCheckerGateway,
                                           ProjectIdCheckerGateway projectIdCheckerGateway) {

        return new CreateClientUseCaseImpl(clientRepositoryGateway, locker, transactionStarter, scriptIdCheckerGateway, projectIdCheckerGateway);
    }

    @Bean
    public MQTTClientManager clientBean9(CreateMQTTClientGateway mqttClient, EventBus eventBus) {
        return new MQTTClientManagerImpl(mqttClient, eventBus);
    }

    @Bean
    public RefreshConnectionsUseCase clientBean10(ClientRepositoryGateway clientRepositoryGateway, MQTTClientManager mqttClientManager) {
        return new RefreshConnectionsUseCaseImpl(clientRepositoryGateway, mqttClientManager);
    }

    @Bean
    public GetAllClientsUseCase clientBean11(ClientRepositoryGateway clientRepositoryGateway, MQTTClientManager mqttClientManager) {
        return new GetAllClientsUseCaseImpl(clientRepositoryGateway, mqttClientManager);
    }

    @Bean
    public GetClientUseCase clientBean12(ClientRepositoryGateway clientRepositoryGateway) {
        return new GetClientUseCaseImpl(clientRepositoryGateway);
    }

    @Bean
    public UpdateClientUseCase clientBean13(Locker locker,
                                            ClientRepositoryGateway clientRepositoryGateway,
                                            TransactionStarter transactionStarter,
                                            MQTTClientManager mqttClientManager,
                                            ScriptIdCheckerGateway scriptIdCheckerGateway) {

        return new UpdateClientUseCaseImpl(locker, clientRepositoryGateway, transactionStarter, mqttClientManager, scriptIdCheckerGateway);
    }

    @Bean
    public MarkConnected clientBean14(ClientRepositoryGateway clientRepositoryGateway,
                                      TimeProvider timeProvider,
                                      Locker locker,
                                      TransactionStarter transactionStarter) {

        return new MarkConnected(clientRepositoryGateway, timeProvider, locker, transactionStarter);
    }


    @Bean
    public MarkDisconnected clientBean15(ClientRepositoryGateway clientRepositoryGateway,
                                         TimeProvider timeProvider,
                                         Locker locker,
                                         TransactionStarter transactionStarter) {

        return new MarkDisconnected(clientRepositoryGateway, timeProvider, locker, transactionStarter);
    }

    @Bean
    public ClientConnectedEventListener clientBean16(EventBus eventBus,
                                                     MarkConnected markConnected,
                                                     ClientConnectedEventHandler clientConnectedEventHandler,
                                                     ClientSubscriptionUpdater clientSubscriptionUpdater) {

        return new ClientConnectedEventListener(eventBus, markConnected, clientConnectedEventHandler, clientSubscriptionUpdater);
    }

    @Bean
    public ClientConnectionExceptionEventListener clientBean17(EventBus eventBus,
                                                               ClientRepositoryGateway clientRepositoryGateway,
                                                               TimeProvider timeProvider,
                                                               Locker locker,
                                                               TransactionStarter transactionStarter) {

        return new ClientConnectionExceptionEventListener(eventBus, clientRepositoryGateway, timeProvider, locker, transactionStarter);
    }

    @Bean
    public ClientDisconnectedEventListener clientBean18(EventBus eventBus,
                                                        ClientDisconnectedEventHandler clientDisconnectedEventHandler,
                                                        MarkDisconnected markDisconnected) {

        return new ClientDisconnectedEventListener(eventBus, markDisconnected, clientDisconnectedEventHandler);
    }

    @Bean
    public DeleteClientUseCase clientBean19(ClientRepositoryGateway clientRepositoryGateway, Locker locker, TransactionStarter transactionStarter) {
        return new DeleteClientUseCaseImpl(locker, clientRepositoryGateway, transactionStarter);
    }

    @Bean
    public ControlClientUseCase clientBean20(Locker locker, ClientRepositoryGateway clientRepositoryGateway, TransactionStarter transactionStarter) {
        return new ControlClientUseCaseImpl(locker, clientRepositoryGateway, transactionStarter);
    }

    @Bean
    public PublishMessageUseCase clientBean21(MQTTClientManager mqttClientManager) {
        return new PublishMessageUseCaseImpl(mqttClientManager);
    }

    @Bean
    public CheckDisconnectedClientsUseCase clientBean22(MQTTClientManager mqttClientManager) {
        return new CheckDisconnectedClientsUseCaseImpl(mqttClientManager);
    }

}
