public class MilkCoffee extends Coffee {
  private Coffee coffee;
  private double milkPrice = 5.0;

  public MilkCoffee(Coffee coffee) {
    this.coffee = coffee;
  }

  public double getPrice() {
    return coffee.getPrice() + milkPrice;
  }

  public String getDescription() {
    return coffee.getDescription() + " with milk";
  }
}
