package library;

import java.util.ArrayList;
import java.util.List;

import model.Book;
import model.Librarian;
import model.Member;

public class Library {
  private static final Library LIBRARY = new Library();
  private List<Librarian> librarians = new ArrayList<>();
  private List<Member> members = new ArrayList<>();
  private List<Book> books = new ArrayList<>();

  private Library() {
    if (LIBRARY != null) {
      throw new UnsupportedOperationException("Only 1 library object can be created, use get instance method");
    }
  }

  public void addLibrarian(Librarian librarian) {
    Librarian currentLibrarian = librarians
      .stream()
      .filter(lib -> lib.getEmail().equalsIgnoreCase(librarian.getEmail()))
      .findFirst()
      .orElse(null);
    if (currentLibrarian == null) {
      librarians.add(librarian);
    }
  }

  public void addMember(Member member) {
    Member currentMember = members
      .stream()
      .filter(men -> !men.getEmail().equalsIgnoreCase(member.getEmail()))
      .findFirst()
      .orElse(null);
    if (currentMember == null) {
      members.add(member);
    }
  }

  public void addBook(Book book) {
    Book currentBook = books
      .stream()
      .filter(b -> b.getTitle().equalsIgnoreCase(book.getTitle()))
      .findFirst()
      .orElse(null);
    if (currentBook == null) {
      books.add(book);
    }
  }

  public static Library getInstance() {
    return LIBRARY;
  }

  public List<Librarian> getLibrarians() {
    return librarians;
  }

  public List<Member> getMembers() {
    return members;
  }

  public List<Book> getBooks() {
    return books;
  }
}
