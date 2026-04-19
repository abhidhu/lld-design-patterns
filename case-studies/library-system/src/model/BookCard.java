package model;

import java.time.LocalDate;
import java.util.Random;

public class BookCard {
  private final Long cardId;
  private final BookCopy bookCopy;
  private final Member member;
  private final LocalDate issueDate;
  private final LocalDate dueDate;
  private LocalDate returnDate;

  public BookCard( BookCopy bookCopy, Member member) {
    this.cardId = new Random().nextLong();
    this.bookCopy = bookCopy;
    this.member = member;
    this.issueDate = LocalDate.now();
    this.dueDate = issueDate.plusDays(7);
  }

  public Long getCardId() {
    return cardId;
  }

  public BookCopy getBookCopy() {
    return bookCopy;
  }

  public Member getMember() {
    return member;
  }

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setReturnDate(LocalDate returnDate) {
    this.returnDate = returnDate;
  }

  public LocalDate getReturnDate() {
    return returnDate;
  }
}
