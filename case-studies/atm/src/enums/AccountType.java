package enums;

public enum AccountType {
  SAVING(40_000, 0),
  CURRENT(40_000, 10_000);

  private final double dailyWithdrawLimit;
  private final double overdraftLimit;

  AccountType(double dailyWithdrawLimit, double overdraftLimit) {
    this.dailyWithdrawLimit = dailyWithdrawLimit;
    this.overdraftLimit = overdraftLimit;
  }

  public double getDailyWithdrawLimit() {
    return dailyWithdrawLimit;
  }

  public double getOverdraftLimit() {
    return overdraftLimit;
  }
}
