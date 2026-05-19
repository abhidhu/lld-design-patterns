package state;

import atm.ATM;
import enums.ATMStateType;

public class OutOfServiceState extends ATMState {

    @Override
    public ATMStateType getStateType() {
        return ATMStateType.OUT_OF_SERVICE;
    }

    // Every action throws — ATM is completely unavailable
}
