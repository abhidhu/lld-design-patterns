package state;

import enums.MachineStateEnum;
import enums.OrderStatus;
import order.Order;
import vendingmachine.VendingMachine;

public class DispensingState extends MachineState {

  private final VendingMachine vendingMachine;

  public DispensingState(VendingMachine vendingMachine) {
    this.vendingMachine = vendingMachine;
  }

  @Override
  public void dispense() {
    System.out.println("Dispensing product...");
    Order currentOrder = vendingMachine.getCurrentOrder();
    try {
      currentOrder.setOrderStatus(OrderStatus.COMPLETED);
      vendingMachine.getSlots().stream()
          .filter(s -> currentOrder.getProduct().equals(s.getProduct()))
          .findFirst()
          .ifPresent(s -> s.setProduct(null));
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      System.out.println("Product dispensed. Thank you for your purchase!");
      vendingMachine.setCurrentOrder(null);
      vendingMachine.setCurrentState(vendingMachine.getStateByEnum(MachineStateEnum.IDLE));
      vendingMachine.checkInventory();
    }
  }
}
