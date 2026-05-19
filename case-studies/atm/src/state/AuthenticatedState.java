package state;

import account.Account;
import atm.ATM;
import enums.ATMStateType;

public class AuthenticatedState extends ATMState {

    @Override
    public ATMStateType getStateType() {
        return ATMStateType.AUTHENTICATED;
    }

    @Override
    public void selectAccount(ATM atm, Account account) {
        atm.setSelectedAccount(account);
        atm.setState(new TransactionState());
        System.out.println(ATMStateType.TRANSACTION.getMessage());
    }

    @Override
    public void ejectCard(ATM atm) {
        atm.setCurrentCard(null);
        atm.setState(new IdleState());
        System.out.println("Card ejected. " + ATMStateType.IDLE.getMessage());
    }
}
