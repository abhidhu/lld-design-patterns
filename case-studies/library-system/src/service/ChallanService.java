package service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import model.BookCard;
import model.Challan;
import model.Librarian;

public class ChallanService {
  private static final ChallanService challanService = new ChallanService();

  private ChallanService(){
    if(challanService != null){
      throw new IllegalCallerException("Can not create more than 1 bean");
    }
  }

  public static ChallanService getInstance(){
    return challanService;
  }
  public void process(BookCard bookCard, Librarian librarian){
    //do logic here
    if(LocalDate.now().isAfter(bookCard.getDueDate())){
      Double amount = 2.0 * ChronoUnit.DAYS.between(bookCard.getDueDate(), LocalDate.now());
      Challan challan = new Challan("Late pannalty", amount, bookCard.getMember(), librarian, bookCard);
      //other process skipping this task
    }
  }
}
