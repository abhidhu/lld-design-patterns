package state;

import account.Card;
import atm.ATM;
import enums.ATMStateType;

public class IdleState extends ATMState {

    @Override
    public ATMStateType getStateType() {
        return ATMStateType.IDLE;
    }

    @Override
    public void insertCard(ATM atm, Card card) {
        if (card.isExpired()) {
            throw new IllegalArgumentException("Card is expired");
        }
        atm.setCurrentCard(card);
        atm.setState(new CardInsertedState());
        System.out.println(ATMStateType.CARD_INSERTED.getMessage());
    }
}
