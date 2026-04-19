package model;

import java.util.Random;

public class Challan {
  private final Long challanId;
  private final String reason;
  private final Double amount;
  private boolean isPaid;
  private final Member member; // The member associated with the challan
  private final Librarian librarian; // The librarian who issued the challan
  private final BookCard bookCard; // The book card associated with the challan (if applicable)

  public Challan(String reason, Double amount, Member member, Librarian librarian, BookCard bookCard) {
    this.challanId = new Random().nextLong();
    this.reason = reason;
    this.amount = amount;
    this.isPaid = false; // By default, a new challan is unpaid
    this.member = member;
    this.librarian = librarian;
    this.bookCard = bookCard;
  }

  public Long getChallanId() {
    return challanId;
  }

  public String getReason() {
    return reason;
  }

  public Double getAmount() {
    return amount;
  }

  public boolean isPaid() {
    return isPaid;
  }

  public void payChallan() {
    this.isPaid = true; // Mark the challan as paid
  }

  public void setPaid(boolean paid) {
    isPaid = paid;
  }
}
