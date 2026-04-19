package observer;

import model.Book;

public interface Observer {
  void update(Book book);
}
