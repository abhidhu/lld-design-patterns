package strategy.search;

import java.util.List;
import java.util.stream.Collectors;

import model.Book;

public class SearchByTitle implements BookSearchStrategy {


  private final static SearchByTitle searchByTitle = new SearchByTitle();

  private SearchByTitle() {
    if (searchByTitle != null) {
      throw new UnsupportedOperationException("only one object creation is allowed");
    }
  }

  public static SearchByTitle getInstance() {
    return searchByTitle;
  }

  @Override
  public List<Book> search(List<Book> books, String query) {
    if (query == null || query.isBlank()) {
      throw new IllegalArgumentException("Please provide title name");
    }
    return books
      .stream()
      .filter(book -> query.equalsIgnoreCase(book.getTitle()))
      .collect(Collectors.toUnmodifiableList());
  }
}
