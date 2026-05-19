package observer;

import transaction.Transaction;

public interface TransactionObserver {
    void onTransactionComplete(Transaction transaction);
}
