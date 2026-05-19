import account.Card;
import account.SavingAccount;
import account.CurrentAccount;
import atm.ATM;
import observer.ReceiptPrinter;
import observer.TransactionLogger;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ATM atm = ATM.getInstance();

        // Register observers
        atm.getTransactionService().addObserver(new ReceiptPrinter());
        atm.getTransactionService().addObserver(new TransactionLogger());

        // Setup accounts and card
        SavingAccount savings = new SavingAccount("ACC001", 50_000);
        CurrentAccount current = new CurrentAccount("ACC002", 10_000);
        Card card = new Card("CARD123", LocalDate.of(2027, 12, 31), List.of(savings, current));

        // Register PIN for the card
        atm.getCardAuthService().registerCard("CARD123", "1234");

        System.out.println("=== Normal withdrawal flow ===");
        atm.insertCard(card);
        atm.enterPIN("1234");
        atm.selectAccount(savings);
        atm.withdraw(5_000);
        atm.ejectCard();

        System.out.println("\n=== Wrong PIN attempt ===");
        atm.insertCard(card);
        try {
            atm.enterPIN("9999");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        atm.ejectCard();

        System.out.println("\n=== Invalid action (withdraw before inserting card) ===");
        try {
            atm.withdraw(1_000);
        } catch (IllegalStateException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        System.out.println("\n=== Overdraft on savings ===");
        atm.insertCard(card);
        atm.enterPIN("1234");
        atm.selectAccount(savings);
        try {
            atm.withdraw(60_000);
        } catch (IllegalStateException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        atm.ejectCard();
    }
}
