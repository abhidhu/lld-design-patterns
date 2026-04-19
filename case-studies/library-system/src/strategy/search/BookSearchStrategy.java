package strategy.search;

import java.util.List;

import model.Book;

public interface BookSearchStrategy {
  List<Book> search(List<Book> books, String query);
}