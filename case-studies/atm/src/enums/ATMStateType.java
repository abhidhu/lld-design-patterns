package enums;

public enum ATMStateType {
    IDLE("Please insert your card"),
    CARD_INSERTED("Card inserted. Please enter your PIN"),
    AUTHENTICATED("PIN verified. Please select an account"),
    TRANSACTION("Account selected. Choose a transaction"),
    OUT_OF_SERVICE("ATM is out of service. Please try another ATM");

    private final String message;

    ATMStateType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
