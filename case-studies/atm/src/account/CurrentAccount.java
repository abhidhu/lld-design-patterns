package account;

import enums.AccountType;

public class CurrentAccount extends Account {
  public CurrentAccount(String accountNo, double balance) {
    super(accountNo, balance);
  }

  @Override
  public AccountType getAccountType() {
    return AccountType.CURRENT;
  }

  @Override
  public double getOverdraftLimit() {
    return AccountType.CURRENT.getOverdraftLimit(); // ₹10,000 overdraft allowed
  }
}
