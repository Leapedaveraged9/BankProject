import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class BankAppUI extends Application {
    private Bank bank;
    private Stage primaryStage;
    private VBox mainMenu;
    private GridPane accountCreationForm;
    private GridPane transactionPanel;
    private VBox balanceInquiryPanel;
    private TableView<Transaction> transactionHistoryViewer;

    @Override
    public void start(Stage primaryStage) {
        bank = new Bank();
        this.primaryStage = primaryStage;

        showMainMenu();
        
        primaryStage.setTitle("Simple Banking System");
        primaryStage.setScene(new Scene(mainMenu, 400, 300));
        primaryStage.show();
    }

    private void showMainMenu() {
        mainMenu = new VBox(10);
        Button createAccountButton = new Button("Create a new account");
        createAccountButton.setOnAction(e -> showAccountCreationForm());
        
        Button depositButton = new Button("Deposit funds");
        depositButton.setOnAction(e -> showTransactionPanel("deposit"));
        
        Button withdrawButton = new Button("Withdraw funds");
        withdrawButton.setOnAction(e -> showTransactionPanel("withdraw"));
        
        Button checkBalanceButton = new Button("Check account balance");
        checkBalanceButton.setOnAction(e -> showBalanceInquiryPanel());
        
        Button viewTransactionsButton = new Button("View transaction history");
        viewTransactionsButton.setOnAction(e -> showTransactionHistoryViewer());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> primaryStage.close());

        mainMenu.getChildren().addAll(createAccountButton, depositButton, withdrawButton, checkBalanceButton, viewTransactionsButton, exitButton);
    }

    private void showAccountCreationForm() {
        accountCreationForm = new GridPane();
        
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label typeLabel = new Label("Account Type:");
        TextField typeField = new TextField();
        Label depositLabel = new Label("Initial Deposit:");
        TextField depositField = new TextField();
        Button createButton = new Button("Create");
        createButton.setOnAction(e -> {
            String name = nameField.getText();
            String type = typeField.getText();
            double deposit = Double.parseDouble(depositField.getText());
            String accountNumber = bank.createAccount(name, type, deposit);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Account Created");
            alert.setHeaderText(null);
            alert.setContentText("Account created successfully. Account Number: " + accountNumber);
            alert.showAndWait();
            primaryStage.setScene(new Scene(mainMenu, 400, 300));
        });

        accountCreationForm.add(nameLabel, 0, 0);
        accountCreationForm.add(nameField, 1, 0);
        accountCreationForm.add(typeLabel, 0, 1);
        accountCreationForm.add(typeField, 1, 1);
        accountCreationForm.add(depositLabel, 0, 2);
        accountCreationForm.add(depositField, 1, 2);
        accountCreationForm.add(createButton, 1, 3);

        primaryStage.setScene(new Scene(accountCreationForm, 400, 200));
    }

    private void showTransactionPanel(String transactionType) {
        transactionPanel = new GridPane();
        
        Label accountLabel = new Label("Account Number:");
        TextField accountField = new TextField();
        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();
        Button transactionButton = new Button(transactionType.equals("deposit") ? "Deposit" : "Withdraw");
        
        transactionButton.setOnAction(e -> {
            String accountNumber = accountField.getText();
            double amount = Double.parseDouble(amountField.getText());
            if (transactionType.equals("deposit")) {
                bank.deposit(accountNumber, amount);
            } else {
                bank.withdraw(accountNumber, amount);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(transactionType.equals("deposit") ? "Deposit Successful" : "Withdrawal Successful");
            alert.setHeaderText(null);
            alert.setContentText(transactionType.equals("deposit") ? "Amount deposited successfully." : "Amount withdrawn successfully.");
            alert.showAndWait();
            primaryStage.setScene(new Scene(mainMenu, 400, 300));
        });

        transactionPanel.add(accountLabel, 0, 0);
        transactionPanel.add(accountField, 1, 0);
        transactionPanel.add(amountLabel, 0, 1);
        transactionPanel.add(amountField, 1, 1);
        transactionPanel.add(transactionButton, 1, 2);

        primaryStage.setScene(new Scene(transactionPanel, 400, 200));
    }

    private void showBalanceInquiryPanel() {
        balanceInquiryPanel = new VBox(10);
        
        Label accountLabel = new Label("Account Number:");
        TextField accountField = new TextField();
        Button checkBalanceButton = new Button("Check Balance");
        Label balanceLabel = new Label();

        checkBalanceButton.setOnAction(e -> {
            String accountNumber = accountField.getText();
            double balance = bank.getBalance(accountNumber);
            balanceLabel.setText("Current Balance: $" + balance);
        });

        balanceInquiryPanel.getChildren().addAll(accountLabel, accountField, checkBalanceButton, balanceLabel);
        
        primaryStage.setScene(new Scene(balanceInquiryPanel, 400, 200));
    }

    private void showTransactionHistoryViewer() {
        transactionHistoryViewer = new TableView<>();
        
        Label accountLabel = new Label("Account Number:");
        TextField accountField = new TextField();
        Button viewButton = new Button("View Transactions");
        
        TableColumn<Transaction, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        TableColumn<Transaction, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        
        TableColumn<Transaction, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        
        TableColumn<Transaction, Double> balanceColumn = new TableColumn<>("Balance After Transaction");
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balanceAfterTransaction"));
        
        transactionHistoryViewer.getColumns().addAll(dateColumn, typeColumn, amountColumn, balanceColumn);

        viewButton.setOnAction(e -> {
            String accountNumber = accountField.getText();
            ArrayList<Transaction> transactions = bank.getTransactionHistory(accountNumber);
            ObservableList<Transaction> observableTransactions = FXCollections.observableArrayList(transactions);
            transactionHistoryViewer.setItems(observableTransactions);
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(accountLabel, accountField, viewButton, transactionHistoryViewer);
        
        primaryStage.setScene(new Scene(vbox, 600, 400));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
