package service;

import java.time.LocalDate;

import enums.NotificationChannelType;
import model.Book;
import model.BookCard;
import model.BookCopy;
import model.Librarian;
import model.Member;
import model.Reservation;

public class BorrowService {

  private static final BorrowService BORROW_SERVICE = new BorrowService();
  private final ChallanService challanService = ChallanService.getInstance();
  private final ReservationService reservationService = ReservationService.getInstance();

  private BorrowService() {
    if (BORROW_SERVICE != null) {
      throw new UnsupportedOperationException("only one borrow service can be exists");
    }
  }

  public static BorrowService getInstance(){
    return BORROW_SERVICE;
  }

  public void borrowBook(Member member, BookCopy bookCopy) {
    // your steps
    member.addBorrowedBook(bookCopy);
    bookCopy.getBookCards().add(new BookCard(bookCopy, member));
  }

  public void returnBook(BookCopy bookCopy, Librarian librarian) {
    // your steps
    BookCard bookCard = bookCopy.getLastCard();
    challanService.process(bookCard, librarian);
    bookCopy.getBook().notifyObservers();
    //    reservationService.update(bookCopy.getBook()); no need to add as from book call will be made
    bookCard.setReturnDate(LocalDate.now());
    bookCard.getMember().removeBorrowedBookCopy(bookCopy);
  }

  public void reserveBook(Member member, Book book, NotificationChannelType notificationChannelType) {
    // your steps
    Reservation reservation = new Reservation(member, book, notificationChannelType);
    book.addReservation(reservation);
    member.addReservations(reservation);
    book.addObserver(reservationService);
  }
}
