package state;

import account.Account;
import account.Card;
import atm.ATM;
import enums.ATMStateType;

public abstract class ATMState {

    public abstract ATMStateType getStateType();

    public void insertCard(ATM atm, Card card) {
        throw new IllegalStateException("Invalid action: " + getStateType().getMessage());
    }

    public void enterPIN(ATM atm, String pin) {
        throw new IllegalStateException("Invalid action: " + getStateType().getMessage());
    }

    public void selectAccount(ATM atm, Account account) {
        throw new IllegalStateException("Invalid action: " + getStateType().getMessage());
    }

    public void withdraw(ATM atm, double amount) {
        throw new IllegalStateException("Invalid action: " + getStateType().getMessage());
    }

    public void deposit(ATM atm, double amount) {
        throw new IllegalStateException("Invalid action: " + getStateType().getMessage());
    }

    public void ejectCard(ATM atm) {
        throw new IllegalStateException("Invalid action: " + getStateType().getMessage());
    }
}
