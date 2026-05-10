package product;

import enums.SlotSize;

public class ProductFactory {
  public static Product createProduct(String productType, Long serialNo, String name, double price, SlotSize slotSize) {
    switch (productType.toLowerCase()) {
      case "chocolate":
        return new Chocolate(serialNo, name, price, slotSize);
      case "chips":
        return new Chips(serialNo, name, price, slotSize);
      case "water":
        return new Water(serialNo, name, price, slotSize);
      default:
        throw new IllegalArgumentException("Invalid product type: " + productType);
    }
  }
}
