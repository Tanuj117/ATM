import java.util.ArrayList;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private String userId;
    String userPin;
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public Account(String userId, String userPin) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public boolean validateUser(String userId, String userPin) {
        return this.userId.equals(userId) && this.userPin.equals(userPin);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount));
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Invalid amount for deposit.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            System.out.println("Withdrawn: $" + amount);
        } else {
            System.out.println("Invalid amount for withdrawal or insufficient balance.");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer to " + recipient.userId, amount));
            System.out.println("Transferred: $" + amount + " to " + recipient.userId);
        } else {
            System.out.println("Invalid amount for transfer or insufficient balance.");
        }
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction.getType() + ": $" + transaction.getAmount());
        }
    }

    public double getBalance() {
        return balance;
    }
}

public class ATM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Account account = new Account("1234", "1234"); // Replace with your user ID and PIN
        boolean loggedIn = false;

        System.out.println("Welcome to the ATM!");
        while (true) {
            if (!loggedIn) {
                System.out.print("Enter User ID: ");
                String userId = scanner.nextLine();
                System.out.print("Enter User PIN: ");
                String userPin = scanner.nextLine();

                if (account.validateUser(userId, userPin)) {
                    loggedIn = true;
                    System.out.println("Logged in successfully!");
                } else {
                    System.out.println("Invalid User ID or PIN. Try again.");
                }
            } else {
                System.out.println("Select an operation:");
                System.out.println("1. Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Transfer");
                System.out.println("4. Deposit");
                System.out.println("5. Quit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        account.displayTransactionHistory();
                        break;
                    case 2:
                        System.out.print("Enter withdrawal amount: $");
                        double withdrawAmount = scanner.nextDouble();
                        account.withdraw(withdrawAmount);
                        break;
                    case 3:
                        System.out.print("Enter recipient's User ID: ");
                        String recipientId = scanner.nextLine();
                        Account recipientAccount = new Account("4321", "4321"); // Replace with recipient's user ID and PIN
                        if (account.validateUser(recipientId, recipientAccount.userPin)) {
                            System.out.print("Enter transfer amount: $");
                            double transferAmount = scanner.nextDouble();
                            account.transfer(recipientAccount, transferAmount);
                        } else {
                            System.out.println("Invalid recipient User ID.");
                        }
                        break;
                    case 4:
                        System.out.print("Enter deposit amount: $");
                        double depositAmount = scanner.nextDouble();
                        account.deposit(depositAmount);
                        break;
                    case 5:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }
}