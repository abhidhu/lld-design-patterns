package state;

import enums.MachineStateEnum;
import vendingmachine.VendingMachine;

public class IdleState extends MachineState {
  private final VendingMachine vendingMachine;

  public IdleState(VendingMachine vendingMachine) {
    this.vendingMachine = vendingMachine;
  }

  @Override
  public void showProducts() {
    this.vendingMachine.displayProducts();
    this.vendingMachine.setCurrentState(this.vendingMachine.getStateByEnum(MachineStateEnum.PRODUCT_SELECT));
  }
}
