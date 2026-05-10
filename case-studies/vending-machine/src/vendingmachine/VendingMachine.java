package vendingmachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.Admin;
import enums.MachineStateEnum;
import enums.NotificationStrategyEnum;
import observer.Observer;
import observer.Subject;
import order.Order;
import product.Slot;
import state.DispensingState;
import state.IdleState;
import state.MachineState;
import state.MoneyInsertedState;
import state.ProductSelectedState;

public class VendingMachine implements Subject {
  private static final VendingMachine INSTANCE = new VendingMachine();
  private static final int MAX_CAPACITY = 100;

  private List<Slot> slots;
  private List<Admin> admins = new ArrayList<>();
  private MachineState currentState;
  private List<Order> orders;
  private Order currentOrder;
  private List<Observer> observers = new ArrayList<>();
  private final Scanner scanner = new Scanner(System.in);

  private VendingMachine() {
    if (INSTANCE != null) {
      throw new RuntimeException("Use getInstance()");
    }
    currentState = new IdleState(this);
  }

  public void setCurrentOrder(Order currentOrder) {
    this.currentOrder = currentOrder;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public List<Slot> getSlots() {
    return slots;
  }

  public List<Admin> getAdmins() {
    return admins;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public Order getCurrentOrder() {
    return currentOrder;
  }

  public static VendingMachine getInstance() {
    return INSTANCE;
  }

  public Scanner getScanner() {
    return scanner;
  }

  public void createAdmin(String name, String id, NotificationStrategyEnum notificationStrategy) {
    Admin admin = new Admin(name, id, notificationStrategy);
    admins.add(admin);
    addObserver(admin);
  }

  public void removeObserver(Observer o) {
    observers.remove(o);
  }

  public void addObserver(Observer o) {
    observers.add(o);
  }

  public void notifyObservers(String message) {
    observers.forEach(o -> o.update(message));
  }

  public void checkInventory() {
    if (slots.size() * 0.2 > getCurrentInventory()) {
      notifyObservers("Inventory low: " + getCurrentInventory() + " items left.");
    }
  }

  private int getCurrentInventory() {
    int total = 0;
    for (Slot slot : slots) {
      total += slot.getProduct() != null ? 1 : 0;
    }
    return total;
  }

  public void selectProduct() {
    currentState.selectProduct();
  }

  public void insertMoney() {
    currentState.insertMoney(currentOrder);
  }

  public void dispense() {
    currentState.dispense();
  }

  public void showProducts() {
    currentState.showProducts();
  }

  void cancel() {
    currentState.cancel();
  }

  public void setCurrentState(MachineState currentState) {
    this.currentState = currentState;
  }

  public MachineState getCurrentState() {
    return currentState;
  }

  public void setSlots(List<Slot> slots) {
    this.slots = slots;
  }

  public MachineState getStateByEnum(MachineStateEnum stateEnum) {
    return switch (stateEnum) {
      case IDLE -> new IdleState(this);
      case PRODUCT_SELECT -> new ProductSelectedState(this);
      case MONEY_INSERT -> new MoneyInsertedState(this);
      case DISPENSING -> new DispensingState(this);
    };
  }

  public void displayProducts() {
    slots
      .stream()
      .filter(s -> s.getProduct() != null)
      .forEach(s -> System.out.println(
        "Product id " + s.getProduct().getSerialNo() + " product name: " + s.getProduct().getName() + " - $" + s
          .getProduct()
          .getPrice()));
  }
}
