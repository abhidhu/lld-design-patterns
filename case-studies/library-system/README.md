# Library Management System

A complete Low Level Design implementation of a Library Management System in plain Java, demonstrating real-world design patterns and SOLID principles.

---

## Problem Statement

Design a Library Management System where:
- A librarian manages books and members
- Members can search, borrow, return and reserve books
- Members get notified when a reserved book becomes available
- Late returns generate a fine (challan)

---

## Functional Requirements

- Member can borrow a book (max 2 books at a time)
- Member can return a book
- Member can reserve a book when unavailable
- Member gets notified when reserved book becomes available
- Member can search books by title, author or ISBN
- Librarian can add, remove and update books and members
- Late return generates a challan with fine amount

## Non-Functional Requirements

- System should handle 10,000+ members and 1,00,000+ books
- System should be extendable without modifying existing code (Open/Closed Principle)
- Notification channels should be pluggable (SMS, Email, WhatsApp)

---

## Design Decisions

### Why BookCopy separate from Book?
A library can have multiple physical copies of the same book. Each copy has its own availability status and borrow history. Keeping BookCopy separate from Book avoids misleading availability status.

### Why Queue for Reservations?
Reservations follow first-come-first-served order. Queue naturally enforces this without extra logic.

### Why Reservation capacity not blocked?
When a member reserves a book, their borrow capacity (max 2) is not blocked. Blocking capacity for a book that may never become available would waste the member's slot. Capacity is only blocked on actual borrow.

### Why ReservationService implements Observer?
ReservationService already manages reservations and knows about members. It is the natural listener for book availability events. This avoids polluting the Member entity with notification logic.

### Why NotificationChannel as Strategy?
Notification delivery (SMS/Email/WhatsApp) can change at runtime based on member preference or system capacity. Strategy pattern allows adding new channels without modifying existing code.

### Priority for notification channel:
1. Channel set on Reservation (per reservation preference)
2. Channel set on Member profile (default preference)
3. System default (EMAIL)

---

## Design Patterns Used

| Pattern | Where Used | Why |
| --- | --- | --- |
| Singleton | Library, LibraryService, ChallanService, ReservationService | Only one instance needed, global access point |
| Observer | Book (Observable), ReservationService (Observer) | Book notifies interested services when copy is returned |
| Strategy | NotificationChannel (SMS/Email) | Swap notification delivery at runtime without code change |
| Facade | LibraryService | Single entry point hiding complexity of borrow, return, reserve operations |
| Template Method | ReservationService | Enforces steps for reservation creation so nothing is missed |

---

## SOLID Principles Applied

| Principle | Where Applied |
| --- | --- |
| Single Responsibility | BookCard only tracks history, ChallanService only calculates fines, each service has one job |
| Open/Closed | Adding new NotificationChannel requires no changes to existing code |
| Liskov Substitution | Any NotificationChannel implementation can replace another without breaking system |
| Interface Segregation | Observer and Observable are separate focused interfaces |
| Dependency Inversion | Services depend on NotificationChannel interface, not concrete SMS or Email class |

---

## Class Structure

```
src/
  model/
    Person (abstract)
    Librarian
    Member
    Book
    BookCopy
    BookCard
    Reservation
    ReservationStatus (enum)
    Challan

  enums/
    NotificationChannelType
    SearchStrategy

  observer/
    Observable (interface)
    Observer (interface)

  strategy/
    search/
      BookSearchStrategy (interface)
      SearchByTitle
      SearchByAuthor
      SearchByISBN
    notification/
      NotificationChannel (interface)
      EmailNotificationChannel
      SmsNotificationChannel

  service/
    LibraryService (Facade)
    BorrowService
    ReservationService
    ChallanService
    BookSearchService

  library/
    Library (Singleton)

  Main
```

---

## Key Flows

### Borrow Flow
```
Member → LibraryService.borrowBook()
       → check member capacity
       → create BookCard entry
       → update BookCopy status
```

### Return Flow
```
Member → LibraryService.returnBook()
       → ChallanService checks late return
       → update BookCard with return date
       → update member borrowed list
       → Book.notifyObservers()
       → ReservationService.update()
       → NotificationChannel.sendNotification()
```

### Reserve Flow
```
Member → LibraryService.reserveBook()
       → create Reservation object
       → add to Book reservation queue
       → add to Member reservation list
       → register ReservationService as Observer on Book
```

---

## Sample Output

```
Librarian added: Akash Dhumal
Members added: Abhijeet Dhumal, Priya Patil
Book added: Clean Code

--- Priya reserves 'Clean Code' ---
Reservation created for: Priya Patil

--- abhijeet searches for 'Clean Code' by title ---
Found: Clean Code by Robert C. Martin

--- abhijeet borrows 'Clean Code' ---
Borrowed by: Abhijeet Dhumal
Borrowed books count: 1

--- abhijeet returns 'Clean Code' late (5 days overdue) ---
SMS Notification has been sent to member Priya Patil for reservation id 3178522296025912487 book name Clean Code

--- System checks reservation and notifies Priya ---
abhijeet's borrowed books after return: 0

--- Testing borrow limit ---
abhijeet borrowed: 2 books
Expected error: Your limit exceeded

 Library System end to end test complete!
```

---

## How to Run

1. Clone the repository
2. Open in IntelliJ IDEA or any Java IDE
3. Run `Main.java`
4. JDK 21+ recommended (uses virtual thread concepts)

---

## What I Learned

- How to identify entities from a problem statement
- How to apply Observer + Strategy together for extensible notification
- Difference between Aggregation and Composition in real scenarios
- How Facade pattern simplifies complex service orchestration
- Importance of Single Responsibility in keeping classes focused and testable
