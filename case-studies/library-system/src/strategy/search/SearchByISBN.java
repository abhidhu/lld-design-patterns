package strategy.search;

import java.util.List;
import java.util.stream.Collectors;

import model.Book;

public class SearchByISBN implements BookSearchStrategy {

  private final static SearchByISBN searchByIsbn = new SearchByISBN();
  private SearchByISBN(){
    if(searchByIsbn != null){
      throw new UnsupportedOperationException("only one object creation is allowed");
    }
  }

  public static SearchByISBN getInstance(){
    return searchByIsbn;
  }

  @Override
  public List<Book> search(List<Book> books, String query) {
    if (query == null || query.isBlank()) {
      throw new IllegalArgumentException("Please provide ISBN name");
    }
    return books
      .stream()
      .filter(book -> query.equalsIgnoreCase(book.getIsbn()))
      .collect(Collectors.toUnmodifiableList());
  }
}
