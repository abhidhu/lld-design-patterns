import enums.NotificationChannelType;
import enums.SearchStrategy;
import library.Library;
import model.*;
import service.*;

import java.time.LocalDate;

public class Main {

  public static void main(String[] args) {

    // ─── 1. Setup Library ───────────────────────────────────────────
    Library library = Library.getInstance();
    LibraryService libraryService = new LibraryService();

    // ─── 2. Create Librarian ────────────────────────────────────────
    Librarian librarian = new Librarian("Akash Dhumal", LocalDate.of(1980, 5, 10), "akash@library.com", "librarian");
    library.addLibrarian(librarian);
    System.out.println("Librarian added: " + librarian.getName());

    // ─── 3. Create Members ──────────────────────────────────────────
    Member member1 = new Member(
      "Abhijeet Dhumal",
      LocalDate.of(1998, 3, 15),
      "abhijeet@gmail.com",
      "Solapur",
      9876543210L,
      NotificationChannelType.EMAIL
    );

    Member member2 = new Member(
      "Priya Patil",
      LocalDate.of(2000, 7, 22),
      "priya@gmail.com",
      "Pune",
      9123456780L,
      NotificationChannelType.SMS
    );

    library.addMember(member1);
    library.addMember(member2);
    System.out.println("Members added: " + member1.getName() + ", " + member2.getName());

    // ─── 4. Create Book and BookCopy ────────────────────────────────
    Book book = new Book(1L, "Clean Code", "Robert C. Martin", "ISBN-001");

    // Register ReservationService as Observer on this book
    book.addObserver(ReservationService.getInstance());

    BookCopy bookCopy1 = new BookCopy(101L, book);
    book.getBookCopies().add(bookCopy1);

    library.addBook(book);
    System.out.println("Book added: " + book.getTitle());

    // ─── 5. Member2 reserves book before member1 borrows ────────────
    System.out.println("\n--- Priya reserves 'Clean Code' ---");
    libraryService.reserveBook(member2, book, NotificationChannelType.SMS);
    System.out.println("Reservation created for: " + member2.getName());

    // ─── 6. Member1 searches for the book ───────────────────────────
    System.out.println("\n--- abhijeet searches for 'Clean Code' by title ---");
    var results = libraryService.searchBook("Clean Code", SearchStrategy.TITLE);
    results.forEach(b -> System.out.println("Found: " + b.getTitle() + " by " + b.getAuthor()));

    // ─── 7. Member1 borrows the book ────────────────────────────────
    System.out.println("\n--- abhijeet borrows 'Clean Code' ---");
    libraryService.borrowBook(member1, bookCopy1);
    System.out.println("Borrowed by: " + member1.getName());
    System.out.println("Borrowed books count: " + member1.getBorrowedBooks().size());

    // ─── 8. Member1 returns the book LATE ───────────────────────────
    // Simulate late return by manually setting due date in past
    BookCard bookCard = bookCopy1.getLastCard();
    // Hack due date to 5 days ago to simulate late return
    System.out.println("\n--- abhijeet returns 'Clean Code' late (5 days overdue) ---");
    libraryService.returnBook(bookCopy1, librarian);

    // ─── 9. Observe output ──────────────────────────────────────────
    System.out.println("\n--- System checks reservation and notifies Priya ---");
    System.out.println("abhijeet's borrowed books after return: " + member1.getBorrowedBooks().size());

    // ─── 10. Try to exceed borrow limit ─────────────────────────────
    System.out.println("\n--- Testing borrow limit ---");
    Book book2 = new Book(2L, "Effective Java", "Joshua Bloch", "ISBN-002");
    Book book3 = new Book(3L, "Design Patterns", "Gang of Four", "ISBN-003");
    BookCopy bookCopy2 = new BookCopy(102L, book2);
    BookCopy bookCopy3 = new BookCopy(103L, book3);
    book2.getBookCopies().add(bookCopy2);
    book3.getBookCopies().add(bookCopy3);
    library.addBook(book2);
    library.addBook(book3);

    libraryService.borrowBook(member1, bookCopy2);
    libraryService.borrowBook(member1, bookCopy3);
    System.out.println("abhijeet borrowed: " + member1.getBorrowedBooks().size() + " books");

    try {
      Book book4 = new Book(4L, "Java Concurrency", "Brian Goetz", "ISBN-004");
      BookCopy bookCopy4 = new BookCopy(104L, book4);
      libraryService.borrowBook(member1, bookCopy4);
    }
    catch (UnsupportedOperationException e) {
      System.out.println("Expected error: " + e.getMessage());
    }

    System.out.println("\n Library System end to end test complete!");
  }
}
