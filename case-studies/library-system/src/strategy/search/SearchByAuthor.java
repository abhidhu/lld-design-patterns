package strategy.search;

import java.util.List;
import java.util.stream.Collectors;

import model.Book;

public class SearchByAuthor implements BookSearchStrategy {

  private final static SearchByAuthor searchByAuthor = new SearchByAuthor();
  private SearchByAuthor(){
    if(searchByAuthor != null){
      throw new UnsupportedOperationException("only one object creation is allowed");
    }
  }

  public static SearchByAuthor getInstance(){
    return searchByAuthor;
  }

  @Override
  public List<Book> search(List<Book> books, String query) {
    if (query == null || query.isBlank()) {
      throw new IllegalArgumentException("Please provide author name");
    }
    return books
      .stream()
      .filter(book -> query.equalsIgnoreCase(book.getAuthor()))
      .collect(Collectors.toUnmodifiableList());
  }
}
