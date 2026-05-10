package state;

import java.util.Optional;

import enums.MachineStateEnum;
import enums.OrderStatus;
import order.Order;
import product.Product;
import product.Slot;
import vendingmachine.VendingMachine;

public class ProductSelectedState extends MachineState {

  private final VendingMachine vendingMachine;

  public ProductSelectedState(VendingMachine vendingMachine) {
    this.vendingMachine = vendingMachine;
  }

  @Override
  public void selectProduct() {
    int i = vendingMachine.getScanner().nextInt();
    Optional<Slot> matchedSlot = vendingMachine.getSlots().stream()
        .filter(s -> s.getProduct() != null)
        .filter(s -> Long.valueOf(i).equals(s.getProduct().getSerialNo()))
        .findFirst();

    if (matchedSlot.isPresent()) {
      Product selectedProduct = matchedSlot.get().getProduct();
      System.out.println("Selected product: " + selectedProduct.getName() + " - $" + selectedProduct.getPrice());
      Order order = new Order(selectedProduct, selectedProduct.getPrice(), null, null);
      vendingMachine.getOrders().add(order);
      vendingMachine.setCurrentOrder(order);
      vendingMachine.setCurrentState(vendingMachine.getStateByEnum(MachineStateEnum.MONEY_INSERT));
    } else {
      System.out.println("Invalid product selection. Please try again.");
    }
  }

  @Override
  public void cancel() {
    Order order = vendingMachine.getCurrentOrder();
    if (order != null) {
      order.setOrderStatus(OrderStatus.CANCELLED);
      vendingMachine.setCurrentOrder(null);
    }
    vendingMachine.setCurrentState(vendingMachine.getStateByEnum(MachineStateEnum.IDLE));
    System.out.println("Order cancelled.");
  }
}
