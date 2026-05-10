package state;

import enums.MachineStateEnum;
import enums.OrderStatus;
import order.Order;
import vendingmachine.VendingMachine;

public class MoneyInsertedState extends MachineState {

  private final VendingMachine vendingMachine;

  public MoneyInsertedState(VendingMachine vendingMachine) {
    this.vendingMachine = vendingMachine;
  }

  @Override
  public void insertMoney(Order order) {
    double money = vendingMachine.getScanner().nextDouble();
    double totalPaid = order.getAmountPaid() + money;
    double amountDue = order.getProduct().getPrice() - totalPaid;
    order.setAmountDue(amountDue);

    if (amountDue > 0) {
      order.setAmountPaid(totalPaid);
      System.out.println("Amount due: $" + amountDue);
      System.out.println("Please insert more money.");
    } else {
      order.setAmountPaid(totalPaid);
      order.setChange(-amountDue);
      order.setOrderStatus(OrderStatus.IN_PROGRESS);
      System.out.println("Payment successful. Change: $" + order.getChange());
      this.vendingMachine.setCurrentState(this.vendingMachine.getStateByEnum(MachineStateEnum.DISPENSING));
    }
  }

  @Override
  public void cancel() {
    Order order = vendingMachine.getCurrentOrder();
    if (order != null) {
      if (order.getAmountPaid() > 0) {
        System.out.println("Refunding $" + order.getAmountPaid());
      }
      order.setOrderStatus(OrderStatus.CANCELLED);
      vendingMachine.setCurrentOrder(null);
    }
    vendingMachine.setCurrentState(vendingMachine.getStateByEnum(MachineStateEnum.IDLE));
    System.out.println("Order cancelled.");
  }
}
