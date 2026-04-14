public class SugarCoffee  extends Coffee {
  private Coffee coffee;
  private double sugarPrice = 2.0;

  public SugarCoffee(Coffee coffee) {
    this.coffee = coffee;;
  }

  public double getPrice() {
    return coffee.getPrice() + sugarPrice;
  }

  public String getDescription() {
    return coffee.getDescription() + " with sugar";
  }
}
