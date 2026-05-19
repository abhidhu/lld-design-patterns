package account;

import enums.AccountType;

public class SavingAccount extends Account {
  public SavingAccount(String accountNo, double balance) {
    super(accountNo, balance);
  }

  @Override
  public AccountType getAccountType() {
    return AccountType.SAVING;
  }

  @Override
  public double getOverdraftLimit() {
    return AccountType.SAVING.getOverdraftLimit(); // 0 — savings can never go negative
  }
}
