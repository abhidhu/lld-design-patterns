package service;

import account.Account;
import atm.ATM;
import dispenser.CashDispenser;
import observer.TransactionObserver;
import state.OutOfServiceState;
import transaction.Transaction;
import transaction.TransactionType;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private final List<TransactionObserver> observers = new ArrayList<>();

    public void addObserver(TransactionObserver observer) {
        observers.add(observer);
    }

    public void withdraw(Account account, CashDispenser dispenser, double amount, ATM atm) {
        // Debit first
        account.withdraw(amount);

        // Then dispense — if it fails, refund
        try {
            dispenser.dispense(amount);
        } catch (IllegalStateException e) {
            account.addBalance(amount); // compensating transaction
            if (dispenser.isEmpty()) {
                atm.setState(new OutOfServiceState());
                System.out.println("ATM is now out of cash. " + atm.getState().getStateType().getMessage());
            }
            throw e;
        }

        notifyObservers(new Transaction(account.getAccountNo(), TransactionType.WITHDRAWAL, amount));
    }

    public void deposit(Account account, double amount) {
        account.addBalance(amount);
        notifyObservers(new Transaction(account.getAccountNo(), TransactionType.DEPOSIT, amount));
    }

    private void notifyObservers(Transaction transaction) {
        for (TransactionObserver observer : observers) {
            observer.onTransactionComplete(transaction);
        }
    }
}
