package product;

import enums.SlotSize;

public class Chocolate extends Product {
  public Chocolate(Long serialNo, String name, double price, SlotSize slotSize) {
    super(serialNo, name, price, slotSize);
  }
}
