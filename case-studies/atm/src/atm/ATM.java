package atm;

import account.Account;
import account.Card;
import dispenser.CashDispenser;
import service.CardAuthService;
import service.TransactionService;
import state.ATMState;
import state.IdleState;

public class ATM {
    private static final ATM INSTANCE = new ATM();

    private ATMState state;
    private Card currentCard;
    private Account selectedAccount;

    private final CashDispenser cashDispenser;
    private final CardAuthService cardAuthService;
    private final TransactionService transactionService;

    private ATM() {
        this.state = new IdleState();
        this.cashDispenser = new CashDispenser(200_000);
        this.cardAuthService = new CardAuthService();
        this.transactionService = new TransactionService();
    }

    public static ATM getInstance() {
        return INSTANCE;
    }

    // --- Actions delegated to current state ---

    public void insertCard(Card card) {
        state.insertCard(this, card);
    }

    public void enterPIN(String pin) {
        state.enterPIN(this, pin);
    }

    public void selectAccount(Account account) {
        state.selectAccount(this, account);
    }

    public void withdraw(double amount) {
        state.withdraw(this, amount);
    }

    public void deposit(double amount) {
        state.deposit(this, amount);
    }

    public void ejectCard() {
        state.ejectCard(this);
    }

    // --- Getters & setters used by states and services ---

    public ATMState getState() { return state; }
    public void setState(ATMState state) { this.state = state; }

    public Card getCurrentCard() { return currentCard; }
    public void setCurrentCard(Card card) { this.currentCard = card; }

    public Account getSelectedAccount() { return selectedAccount; }
    public void setSelectedAccount(Account account) { this.selectedAccount = account; }

    public CashDispenser getCashDispenser() { return cashDispenser; }
    public CardAuthService getCardAuthService() { return cardAuthService; }
    public TransactionService getTransactionService() { return transactionService; }
}
