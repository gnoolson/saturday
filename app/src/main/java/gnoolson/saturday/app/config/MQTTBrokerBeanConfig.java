package gnoolson.saturday.app.config;

import gnoolson.locker.Locker;
import gnoolson.saturday.app.mqtt.broker.MoquetteMQTTBroker;
import gnoolson.saturday.app.mqtt.broker.SaturdayIAuthenticator;
import gnoolson.saturday.app.mqtt.broker.SaturdayInterceptor;
import gnoolson.saturday.app.repository.key_value.KVJPARepository;
import gnoolson.saturday.app.repository.mqtt_broker.MQTTBrokerStateRepositoryGatewayImpl;
import gnoolson.saturday.app.repository.mqtt_broker.UserRepositoryGatewayImpl;
import gnoolson.saturday.broker.application.*;
import gnoolson.saturday.broker.model.MQTTBroker;
import gnoolson.saturday.broker.port.inbount.*;
import gnoolson.saturday.broker.port.outbount.ConnectedClientsProviderGateway;
import gnoolson.saturday.broker.port.outbount.MQTTBrokerStateRepositoryGateway;
import gnoolson.saturday.broker.port.outbount.UserRepositoryGateway;
import gnoolson.saturday.common.time.TimeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.FileNotFoundException;


@Configuration
public class MQTTBrokerBeanConfig {

    @Bean(destroyMethod = "shutdown")
    public MQTTBroker mqttBrokerBean1(SaturdayIAuthenticator saturdayIAuthenticator, SaturdayInterceptor saturdayInterceptor) {
        return new MoquetteMQTTBroker(saturdayIAuthenticator, saturdayInterceptor);
    }

    @Bean
    public StartMQTTBrokerUseCase mqttBrokerBean2(MQTTBroker mqttBroker, MQTTBrokerStateRepositoryGateway mqttBrokerStateRepositoryGateway) {
        return new StartMQTTBrokerUseCaseImpl(mqttBroker, mqttBrokerStateRepositoryGateway);
    }

    @Bean
    public ShutdownMQTTBrokerUseCase mqttBrokerBean3(MQTTBroker mqttBroker, MQTTBrokerStateRepositoryGateway mqttBrokerStateRepositoryGateway, ClientSessionStorage storage) {
        return new ShutdownMQTTBrokerUseCaseImpl(mqttBroker, mqttBrokerStateRepositoryGateway, storage);
    }

    @Bean
    public GetMQTTBrokerUseCase mqttBrokerBean4(MQTTBrokerStateRepositoryGateway mqttBrokerStateRepositoryGateway) {
        return new GetMQTTBrokerUseCaseImpl(mqttBrokerStateRepositoryGateway);
    }

    @Bean
    public MQTTBrokerStateRepositoryGateway mqttBrokerBean5(KVJPARepository kvjpaRepository) {
        return new MQTTBrokerStateRepositoryGatewayImpl(kvjpaRepository);
    }

    @Bean
    public RestorePreviousStateUseCase mqttBrokerBean6(MQTTBrokerStateRepositoryGateway mqttBrokerStateRepositoryGateway, StartMQTTBrokerUseCase startMQTTBrokerUseCase) {
        return new RestorePreviousStateUseCaseImpl(mqttBrokerStateRepositoryGateway, startMQTTBrokerUseCase);
    }

    @Bean
    public GetMQTTClientsUseCaseImpl mqttBrokerBean7(ConnectedClientsProviderGateway connectedClientsProviderGateway) {
        return new GetMQTTClientsUseCaseImpl(connectedClientsProviderGateway);
    }

    @Bean
    public SaturdayIAuthenticator mqttBrokerBean8(UserRepositoryGateway userRepositoryGateway) {
        return new SaturdayIAuthenticator(userRepositoryGateway);
    }

    @Bean
    public SaturdayInterceptor mqttBrokerBean9(ClientSessionStorage storage) {
        return new SaturdayInterceptor(storage);
    }

    @Bean
    public GetUsersUseCase mqttBrokerBean10(UserRepositoryGateway UserRepositoryGateway) {
        return new GetUsersUseCaseImpl(UserRepositoryGateway);
    }

    @Bean
    public CreateUserUseCase mqttBrokerBean11(UserRepositoryGateway UserRepositoryGateway, Locker locker) {
        return new CreateUserUseCaseImpl(UserRepositoryGateway, locker);
    }

    @Bean
    public UserRepositoryGateway mqttBrokerBean12(KVJPARepository kvjpaRepository, BCryptPasswordEncoder passwordEncoder) {
        return new UserRepositoryGatewayImpl(kvjpaRepository, passwordEncoder);
    }

    @Bean
    public DeleteUserUseCase mqttBrokerBean13(UserRepositoryGateway UserRepositoryGateway, Locker locker) {
        return new DeleteUserUseCaseImpl(UserRepositoryGateway, locker);
    }

    @Bean
    public ClientSessionStorage mqttBrokerBean14(TimeProvider timeProvider){
        return new ClientSessionStorage(timeProvider);
    }

    @Bean
    public ConnectedClientsProviderGateway mqttBrokerBean15(ClientSessionStorage storage){
        return new ConnectedClientsProviderGatewayImpl(storage);
    }

}
