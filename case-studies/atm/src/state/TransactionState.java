package state;

import atm.ATM;
import enums.ATMStateType;

public class TransactionState extends ATMState {

    @Override
    public ATMStateType getStateType() {
        return ATMStateType.TRANSACTION;
    }

    @Override
    public void withdraw(ATM atm, double amount) {
        atm.getTransactionService().withdraw(atm.getSelectedAccount(), atm.getCashDispenser(), amount, atm);
        System.out.println("Withdrew ₹" + amount + " successfully");
    }

    @Override
    public void deposit(ATM atm, double amount) {
        atm.getTransactionService().deposit(atm.getSelectedAccount(), amount);
        System.out.println("Deposited ₹" + amount + " successfully");
    }

    @Override
    public void ejectCard(ATM atm) {
        atm.setCurrentCard(null);
        atm.setSelectedAccount(null);
        atm.setState(new IdleState());
        System.out.println("Card ejected. " + ATMStateType.IDLE.getMessage());
    }
}
