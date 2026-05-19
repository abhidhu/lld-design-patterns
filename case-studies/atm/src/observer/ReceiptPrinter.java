package observer;

import transaction.Transaction;

public class ReceiptPrinter implements TransactionObserver {

    @Override
    public void onTransactionComplete(Transaction transaction) {
        System.out.println("---- RECEIPT ----");
        System.out.println("Transaction ID : " + transaction.getTransactionId());
        System.out.println("Account        : " + transaction.getAccountNo());
        System.out.println("Type           : " + transaction.getType());
        System.out.println("Amount         : ₹" + transaction.getAmount());
        System.out.println("Time           : " + transaction.getTimestamp());
        System.out.println("-----------------");
    }
}
