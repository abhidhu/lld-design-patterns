package observer;

import transaction.Transaction;

public class TransactionLogger implements TransactionObserver {

    @Override
    public void onTransactionComplete(Transaction transaction) {
        System.out.println("[LOG] " + transaction.getTimestamp()
                + " | " + transaction.getType()
                + " | Account: " + transaction.getAccountNo()
                + " | Amount: ₹" + transaction.getAmount()
                + " | TxnId: " + transaction.getTransactionId());
    }
}
