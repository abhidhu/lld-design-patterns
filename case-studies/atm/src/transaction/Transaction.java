package transaction;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private final String transactionId;
    private final String accountNo;
    private final TransactionType type;
    private final double amount;
    private final LocalDateTime timestamp;

    public Transaction(String accountNo, TransactionType type, double amount) {
        this.transactionId = UUID.randomUUID().toString();
        this.accountNo = accountNo;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public String getTransactionId() { return transactionId; }
    public String getAccountNo() { return accountNo; }
    public TransactionType getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
