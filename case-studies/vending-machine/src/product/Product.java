package product;

import enums.SlotSize;

public abstract class Product {
  private Long serialNo;
  private String name;
  private double price;
  private SlotSize slotSize;

  public Product(Long serialNo, String name, double price, SlotSize slotSize) {
    this.serialNo = serialNo;
    this.name = name;
    this.price = price;
    this.slotSize = slotSize;
  }

  public Long getSerialNo() {
    return serialNo;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public SlotSize getSlotSize() {
    return slotSize;
  }
}
