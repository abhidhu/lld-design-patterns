import java.util.List;

public class CoffeeMachine {
  private static CoffeeMachine coffeeMachine = new CoffeeMachine();
  private CoffeeFactory coffeeFactory = new CoffeeFactory();

  private CoffeeMachine() {
    if (coffeeMachine != null) {
      throw new RuntimeException("Connot created another object");
    }
  }

  public static CoffeeMachine getInstance() {
    return coffeeMachine;
  }

  public Coffee makeCoffee(CoffeeType type, List<CoffeeDecorators> decorators) {
    return coffeeFactory.makeCoffee(type, decorators);
  }

}
