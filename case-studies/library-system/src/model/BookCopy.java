package model;

import java.util.ArrayList;
import java.util.List;

public class BookCopy {
  private final Long bookCopyId;
  private final Book book;
  boolean isAvailable;
  private List<BookCard> bookCards = new ArrayList<>();

  public BookCopy(Long bookCopyId, Book book) {
    this.bookCopyId = bookCopyId;
    this.book = book;
    this.isAvailable = true; // By default, a new book copy is available
    book.getBookCopies().add(this); // Add this copy to the book's list of copies
  }

  public Long getBookCopyId() {
    return bookCopyId;
  }

  public Book getBook() {
    return book;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public List<BookCard> getBookCards() {
    return bookCards;
  }


  public void setAvailable(boolean available) {
    isAvailable = available;
  }

  public void setBookCards(List<BookCard> bookCards) {
    this.bookCards = bookCards;
  }

  public BookCard getLastCard() {
    if (bookCards.isEmpty()) {
      return null;
    }
    return bookCards.get(bookCards.size() - 1);
  }
}
