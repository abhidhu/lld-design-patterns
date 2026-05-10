package state;

import order.Order;

public abstract class MachineState {

  public void showProducts() {
    System.out.println("Invalid action");
  }

  public void selectProduct() {
    System.out.println("Invalid action");
  }

  public void insertMoney(Order order) {
    System.out.println("Invalid action");
  }

  public void dispense() {
    System.out.println("Invalid action");
  }

  public void cancel() {
    System.out.println("Invalid action");
  }
}

