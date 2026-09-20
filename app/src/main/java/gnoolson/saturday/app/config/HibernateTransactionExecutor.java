package gnoolson.saturday.app.config;

import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class HibernateTransactionExecutor {

    @Transactional
    public void doItInTransaction(TransactionWrapper transactionWrapper) {
        transactionWrapper.exec();
    }

    public interface TransactionWrapper {
        void exec();
    }

}
