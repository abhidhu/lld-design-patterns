package model;

import java.time.LocalDate;
import java.util.Random;

import enums.ReservationStatus;
import enums.NotificationChannelType;

public class Reservation {
  private final Long reservationId;
  private final Member member;
  private final Book book;
  private final LocalDate reservationDate;
  private ReservationStatus reservationStatus;
  private NotificationChannelType notificationChannelType;

  public Reservation(Member member, Book book){
    reservationId = new Random().nextLong();
    this.member = member;
    this.book = book;
    reservationDate = LocalDate.now();
    reservationStatus = ReservationStatus.OPEN;
  }

  public Reservation(Member member, Book book, NotificationChannelType notificationChannelType){
    this(member, book);
    this.notificationChannelType = notificationChannelType;
  }

  public void setReservationStatus(ReservationStatus reservationStatus) {
    this.reservationStatus = reservationStatus;
  }

  public Long getReservationId() {
    return reservationId;
  }

  public Member getMember() {
    return member;
  }

  public Book getBook() {
    return book;
  }

  public LocalDate getReservationDate() {
    return reservationDate;
  }

  public ReservationStatus getReservationStatus() {
    return reservationStatus;
  }

  public NotificationChannelType getNotificationChannelType() {
    return notificationChannelType;
  }

  public void setNotificationChannelType(NotificationChannelType notificationChannelType) {
    this.notificationChannelType = notificationChannelType;
  }
}
