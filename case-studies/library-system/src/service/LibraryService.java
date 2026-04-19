package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enums.NotificationChannelType;
import enums.SearchStrategy;
import library.Library;
import model.Book;
import model.BookCopy;
import model.Librarian;
import model.Member;
import strategy.search.BookSearchStrategy;
import strategy.search.SearchByAuthor;
import strategy.search.SearchByISBN;
import strategy.search.SearchByTitle;

public class LibraryService {
  private final Library library = Library.getInstance();
  private final BorrowService borrowService = BorrowService.getInstance();
  //  private final ReservationService reservationService = ReservationService.getInstance();
  private final Map<SearchStrategy, BookSearchStrategy> bookSearchServiceMap = new HashMap<>();

  public LibraryService() {
    bookSearchServiceMap.put(SearchStrategy.AUTHOR, SearchByAuthor.getInstance());
    bookSearchServiceMap.put(SearchStrategy.TITLE, SearchByTitle.getInstance());
    bookSearchServiceMap.put(SearchStrategy.ISBN, SearchByISBN.getInstance());
  }

  public void borrowBook(Member member, BookCopy bookCopy) {
    borrowService.borrowBook(member, bookCopy);
  }

  public void returnBook(BookCopy bookCopy, Librarian librarian) {
    borrowService.returnBook(bookCopy, librarian);
  }

  public void reserveBook(Member member, Book book, NotificationChannelType notificationChannelType) {
    borrowService.reserveBook(member, book, notificationChannelType);
  }

  public List<Book> searchBook(String query, SearchStrategy searchStrategy) {
    BookSearchStrategy bookSearchStrategy = bookSearchServiceMap.get(searchStrategy);
    if (bookSearchStrategy == null) {
      bookSearchStrategy = bookSearchServiceMap.get(SearchStrategy.TITLE);
    }
    return bookSearchStrategy.search(library.getBooks(), query);
  }

}
