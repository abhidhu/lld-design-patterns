package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enums.SearchStrategy;
import model.Book;
import strategy.search.BookSearchStrategy;
import strategy.search.SearchByAuthor;
import strategy.search.SearchByISBN;
import strategy.search.SearchByTitle;

public class BookSearchService {

  private final static BookSearchService bookSearchService = new BookSearchService();
  private static final Map<SearchStrategy, BookSearchStrategy> strategyHashMap = new HashMap<>();

  static {
    strategyHashMap.put(SearchStrategy.AUTHOR, SearchByAuthor.getInstance());
    strategyHashMap.put(SearchStrategy.ISBN, SearchByISBN.getInstance());
    strategyHashMap.put(SearchStrategy.TITLE, SearchByTitle.getInstance());
  }

  private BookSearchService() {
    if (bookSearchService != null) {
      throw new UnsupportedOperationException("Only object can be created of  this class");
    }
  }

  public static BookSearchService getInstance() {
    return bookSearchService;
  }

  public List<Book> findBook(List<Book> books, String query, SearchStrategy searchStrategy) {
    BookSearchStrategy bookSearchStrategy = strategyHashMap.get(searchStrategy);
    if(bookSearchStrategy == null){
      bookSearchStrategy = strategyHashMap.get(SearchStrategy.AUTHOR);
    }
    return bookSearchStrategy.search(books, query);
  }
}
