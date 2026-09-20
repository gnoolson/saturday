package gnoolson.saturday.app.config;

import gnoolson.locker.Locker;
import gnoolson.locker.OptimisticLocalLocker;
import gnoolson.saturday.app.log.SaturdayLog;
import gnoolson.saturday.app.log.SaturdayLogImpl;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.local.LocalEventBus;
import gnoolson.saturday.common.time.TimeProvider;
import gnoolson.saturday.common.time.vo.TimeInMs;
import gnoolson.saturday.common.transaction.*;
import gnoolson.saturday.script.port.outbound.ScriptLogReaderGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Configuration
public class BeanConfig {

    @Bean
    public EventBus eventBus() {
        return new LocalEventBus();
    }

    @Bean
    public Locker locker() {
        return new OptimisticLocalLocker(1, 10, 5000);
    }

    @Bean
    public TransactionStarter transactionStarter(HibernateTransactionExecutor hibernateTransactionExecutor) {
        return new TransactionStarter(new TransactionSynchronizationManagerWrapper() {
            @Override
            public void register(TransactionSynchronizationAdapterWrapper wrapper) {
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        wrapper.exec();
                    }
                });
            }
        }, new TransactionExecutor() {
            @Override
            public void exec(TransactionBody transactionBody) {
                hibernateTransactionExecutor.doItInTransaction(transactionBody::exec);
            }
        });
    }

    @Bean
    public TimeProvider timeProvider() {
        return new TimeProvider() {
            @Override
            public TimeInMs now() {
                return TimeInMs.of(System.currentTimeMillis());
            }
        };
    }

    @Bean
    public MessageSource messageSource(@Value("${gnoolson.saturday.web.messages.path}") String pathToMessages) {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(pathToMessages + "messages");
        messageSource.setCacheSeconds(10);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public SaturdayLog saturdayLog(ScriptLogReaderGateway scriptLogReaderGateway) throws FileNotFoundException {
        File file = ResourceUtils.getFile("file:./log/application.log");
        return new SaturdayLogImpl(file, scriptLogReaderGateway);
    }

}
