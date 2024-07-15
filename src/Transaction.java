import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private String transactionID;
    private LocalDateTime date;
    private String type;
    private double amount;
    private double balanceAfterTransaction;

    public Transaction(String type, double amount, double balanceAfterTransaction) {
        this.transactionID = UUID.randomUUID().toString();
        this.date = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }
}
