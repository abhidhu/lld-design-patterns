package account;

import enums.AccountType;

import java.time.LocalDate;

public abstract class Account {
  private final String accountNo;
  private volatile double balance;
  private volatile double dailyWithdrawnAmount;
  private LocalDate lastResetDate;

  public Account(String accountNo, double balance) {
    this.accountNo = accountNo;
    this.balance = balance;
    this.dailyWithdrawnAmount = 0;
    this.lastResetDate = LocalDate.now();
  }

  public synchronized void addBalance(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Deposit amount must be positive");
    }
    this.balance += amount;
  }

  public synchronized void withdraw(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Withdrawal amount must be positive");
    }

    resetDailyLimitIfNeeded();

    double dailyLimit = getAccountType().getDailyWithdrawLimit();
    if (dailyWithdrawnAmount + amount > dailyLimit) {
      throw new IllegalStateException("Daily withdrawal limit of ₹" + dailyLimit + " exceeded");
    }
    if (balance - amount < -getOverdraftLimit()) {
      throw new IllegalStateException("Insufficient balance");
    }

    this.balance -= amount;
    this.dailyWithdrawnAmount += amount;
  }

  private void resetDailyLimitIfNeeded() {
    if (!lastResetDate.equals(LocalDate.now())) {
      dailyWithdrawnAmount = 0;
      lastResetDate = LocalDate.now();
    }
  }

  public String getAccountNo() {
    return accountNo;
  }

  public double getBalance() {
    return balance;
  }

  public double getDailyWithdrawnAmount() {
    return dailyWithdrawnAmount;
  }

  public LocalDate getLastResetDate() {
    return lastResetDate;
  }

  public abstract AccountType getAccountType();

  public abstract double getOverdraftLimit();
}
