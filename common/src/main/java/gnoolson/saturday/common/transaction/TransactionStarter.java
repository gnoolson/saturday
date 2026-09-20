package gnoolson.saturday.common.transaction;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactionStarter {

    private final TransactionSynchronizationManagerWrapper transactionSynchronizationManagerWrapper;
    private final TransactionExecutor transactionExecutor;

    public void doAfterTransactionCommit(Callback callback) {
        transactionSynchronizationManagerWrapper.register(callback::exec);
    }

    public void doIt(TransactionBody body) {
        transactionExecutor.exec(body);
    }

}
