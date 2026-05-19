package account;

import java.time.LocalDate;
import java.util.List;

public class Card {
  private final String cardNo;
  private final LocalDate expiryDate;
  private final List<Account> accounts;

  public Card(String cardNo, LocalDate expiryDate, List<Account> accounts) {
    this.cardNo = cardNo;
    this.expiryDate = expiryDate;
    this.accounts = accounts;
  }

  public String getCardNo() {
    return cardNo;
  }

  public LocalDate getExpiryDate() {
    return expiryDate;
  }

  public List<Account> getAccounts() {
    return accounts;
  }

  public boolean isExpired() {
    return LocalDate.now().isAfter(expiryDate);
  }
}
