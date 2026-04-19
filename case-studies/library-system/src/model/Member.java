package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import enums.NotificationChannelType;

public class Member extends Person {
  private Long membershipId;
  private String address;
  private Long phoneNumber;
  private NotificationChannelType notificationChannelType;
  private final List<BookCopy> borrowedBooks = new ArrayList<>();
  private final List<Reservation> reservations = new ArrayList<>();
  private int MAX_BORROWED_LIMIT = 2;


  public Member(String name, LocalDate dateOfBirth, String email, String address, Long phoneNumber) {
    super(name, dateOfBirth, email);
    this.membershipId = new Random().nextLong();
    this.address = address;
    this.phoneNumber = phoneNumber;
  }

  public Member(
    String name,
    LocalDate dateOfBirth,
    String email,
    String address,
    Long phoneNumber,
    NotificationChannelType notificationChannelType
  ) {
    this(name, dateOfBirth, email, address, phoneNumber);
    this.notificationChannelType = notificationChannelType;
  }

  public Long getMembershipId() {
    return membershipId;
  }

  public String getAddress() {
    return address;
  }

  public Long getPhoneNumber() {
    return phoneNumber;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setPhoneNumber(Long phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public NotificationChannelType getNotificationChannelType() {
    return notificationChannelType;
  }

  public void setNotificationChannelType(NotificationChannelType notificationChannelType) {
    this.notificationChannelType = notificationChannelType;
  }

  public List<BookCopy> getBorrowedBooks() {
    return List.copyOf(borrowedBooks);
  }

  public void addBorrowedBook(BookCopy bookCopy) {
    if (MAX_BORROWED_LIMIT <= borrowedBooks.size()) {
      throw new UnsupportedOperationException("Your limit exceeded");
    }
    borrowedBooks.add(bookCopy);
  }

  public void removeBorrowedBookCopy(BookCopy borrowedBook) {
    BookCopy actualCopy = borrowedBooks
      .stream()
      .filter(br -> br.getBookCopyId().equals(borrowedBook.getBookCopyId()))
      .findFirst()
      .orElse(null);
    if (actualCopy != null) {
      borrowedBooks.remove(actualCopy);
    }
    borrowedBooks.remove(borrowedBook);
  }

  public List<Reservation> getReservations() {
    return List.copyOf(reservations);
  }

  public void addReservations(Reservation reservation) {
    reservations.add(reservation);
  }

  public void removeReservation(Reservation reservation) {
    Reservation actualReservation = reservations
      .stream()
      .filter(res -> res.getReservationId().equals(reservation.getReservationId()))
      .findFirst()
      .orElse(null);
    if (actualReservation != null) {
      reservations.remove(actualReservation);
    }
  }
}
