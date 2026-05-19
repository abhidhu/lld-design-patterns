package state;

import atm.ATM;
import enums.ATMStateType;

public class CardInsertedState extends ATMState {

    @Override
    public ATMStateType getStateType() {
        return ATMStateType.CARD_INSERTED;
    }

    @Override
    public void enterPIN(ATM atm, String pin) {
        boolean valid = atm.getCardAuthService().authenticate(atm.getCurrentCard().getCardNo(), pin);
        if (!valid) {
            throw new IllegalArgumentException("Invalid PIN");
        }
        atm.setState(new AuthenticatedState());
        System.out.println(ATMStateType.AUTHENTICATED.getMessage());
    }

    @Override
    public void ejectCard(ATM atm) {
        atm.setCurrentCard(null);
        atm.setState(new IdleState());
        System.out.println("Card ejected. " + ATMStateType.IDLE.getMessage());
    }
}
