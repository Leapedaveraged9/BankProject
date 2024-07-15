import java.util.ArrayList;

public class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private String accountType;
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public BankAccount(String accountNumber, String accountHolderName, String accountType, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance = initialDeposit;
        this.transactionHistory = new ArrayList<>();
    }

    public void deposit(double amount) {
        balance += amount;
        addTransaction(new Transaction("deposit", amount, balance));
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            addTransaction(new Transaction("withdrawal", amount, balance));
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public double getBalance() {
        return balance;
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getAccountType() {
        return accountType;
    }
}
