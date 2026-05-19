package dispenser;

public class CashDispenser {
    private double availableCash;

    public CashDispenser(double initialCash) {
        this.availableCash = initialCash;
    }

    public boolean hasSufficientCash(double amount) {
        return availableCash >= amount;
    }

    public boolean isEmpty() {
        return availableCash <= 0;
    }

    public void dispense(double amount) {
        if (!hasSufficientCash(amount)) {
            throw new IllegalStateException("Insufficient cash in ATM. Available: ₹" + availableCash);
        }
        availableCash -= amount;
        System.out.println("Dispensing ₹" + amount + " | Remaining in ATM: ₹" + availableCash);
    }

    public double getAvailableCash() {
        return availableCash;
    }
}
