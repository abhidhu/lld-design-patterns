package product;

import enums.SlotSize;

public class Slot {
  private SlotSize slotSize;
  private Product product;

  public Slot(SlotSize slotSize, Product product) {
    this.slotSize = slotSize;
    this.product = product;
  }

  public SlotSize getSlotSize() {
    return slotSize;
  }

  public void setSlotSize(SlotSize slotSize) {
    this.slotSize = slotSize;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
