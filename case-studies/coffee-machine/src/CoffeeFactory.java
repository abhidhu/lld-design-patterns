import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CoffeeFactory {

  private static Map<CoffeeType, Supplier<Coffee>> coffeeTypeCoffeeMap = new HashMap<>();

  static {
    coffeeTypeCoffeeMap.put(CoffeeType.LATTE, Latte::new);
    coffeeTypeCoffeeMap.put(CoffeeType.AMERICANO, Americano::new);
  }

  public Coffee makeCoffee(CoffeeType type) {
    Supplier<Coffee> supplier = coffeeTypeCoffeeMap.get(type);
    if (supplier == null){
      throw new RuntimeException("Invalid coffee type");
    }
    Coffee coffee = supplier.get();
    if(coffee != null){
      return coffee;
    }
    throw new RuntimeException("Invalid coffee type");
  }

  public Coffee makeCoffee(CoffeeType type, List<CoffeeDecorators>  coffeeDecorators) {
    Supplier<Coffee> supplier = coffeeTypeCoffeeMap.get(type);
    if (supplier == null){
      throw new RuntimeException("Invalid coffee type");
    }
    Coffee coffee = supplier.get();
    if(coffee == null){
      throw new RuntimeException("Invalid coffee type");
    }
    for(CoffeeDecorators coffecoffeeDecorator: coffeeDecorators){
      coffee = switch (coffecoffeeDecorator) {
        case SUGAR -> new SugarCoffee(coffee);
        case MILK -> new MilkCoffee(coffee);
        default -> coffee;
      };

    }
    return coffee;
  }



}
