import java.util.List;

public class Main {
  public static void main(String[] args){
    CoffeeMachine coffeeMachine = CoffeeMachine.getInstance();
    Coffee latte = coffeeMachine.makeCoffee(CoffeeType.LATTE, List.of());
    System.out.println("Price: " + latte.getPrice() + " Description " + latte.getDescription());

    Coffee americano = coffeeMachine.makeCoffee(CoffeeType.AMERICANO, List.of());
    System.out.println("Price: " + americano.getPrice() + " Description " + americano.getDescription());

    Coffee latteWithMilk = coffeeMachine.makeCoffee(CoffeeType.LATTE, List.of(CoffeeDecorators.MILK));
    System.out.println("Price: " + latteWithMilk.getPrice() + " Description " + latteWithMilk.getDescription());

  }
}
