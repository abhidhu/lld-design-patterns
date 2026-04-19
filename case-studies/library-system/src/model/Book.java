package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import observer.Observable;
import observer.Observer;

public class Book implements Observable {
  private final Long bookId;
  private final String title;
  private final String author;
  private final String isbn;
  private final List<BookCopy> bookCopies = new ArrayList<>();
  private List<Observer> observers = new ArrayList<>();
  private final Queue<Reservation> reservations = new LinkedList<>();

  public Book(Long bookId, String title, String author, String isbn) {
    this.bookId = bookId;
    this.title = title;
    this.author = author;
    this.isbn = isbn;
  }

  public Long getBookId() {
    return bookId;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getIsbn() {
    return isbn;
  }

  public List<BookCopy> getBookCopies() {
    return bookCopies;
  }

  public void addReservation(Reservation reservation) {
    reservations.add(reservation);
  }

  public List<Reservation> getReservations() {
    return List.copyOf(reservations);
  }

  @Override
  public void addObserver(Observer observer) {
    if (observer == null) {
      throw new IllegalArgumentException("Null observer provided");
    }
    Observer existingObserver = observers.stream().filter(ob -> ob.equals(observer)).findFirst().orElse(null);
    if (existingObserver == null) {
      observers.add(observer);
    }
  }

  @Override
  public void removeObserver(Observer observer) {
    observers = observers.stream().filter(obj -> !obj.equals(observer)).collect(Collectors.toList());
  }

  @Override
  public void notifyObservers() {
    observers.forEach(obj -> obj.update(this));
  }
}
