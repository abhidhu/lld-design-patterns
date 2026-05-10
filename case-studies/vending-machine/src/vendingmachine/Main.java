package vendingmachine;

import enums.NotificationStrategyEnum;
import enums.SlotSize;
import product.Product;
import product.ProductFactory;
import product.Slot;

import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) {

    // 1. Get VendingMachine singleton
    VendingMachine vm = VendingMachine.getInstance();

    // 2. Create products using factory
    Product chips = ProductFactory.createProduct("chips", 1L, "Lays Classic", 20.0, SlotSize.SMALL);
    Product water = ProductFactory.createProduct("water", 2L, "Bisleri", 15.0, SlotSize.MEDIUM);
    Product chocolate = ProductFactory.createProduct("chocolate", 3L, "Dairy Milk", 30.0, SlotSize.SMALL);

    // 3. Create slots and add products
    List<Slot> slots = new ArrayList<>();
    slots.add(new Slot(SlotSize.SMALL, chips));
    slots.add(new Slot(SlotSize.MEDIUM, water));
    slots.add(new Slot(SlotSize.SMALL, chocolate));
    vm.setSlots(slots);

    // 4. Create orders list
    vm.setOrders(new ArrayList<>());

    // 5. Create admin with SMS notification
    vm.createAdmin("Abhishek", "A001", NotificationStrategyEnum.SMS);

    System.out.println("=== Vending Machine Started ===\n");

    // 6. Show products (IdleState → ProductSelectedState)
    System.out.println("--- Step 1: Show Products ---");
    vm.showProducts();

    // 7. Select product by serialNo
    // (will ask for input — type: 1 for Chips)
    System.out.println("\n--- Step 2: Select Product (enter serialNo) ---");
    vm.selectProduct();

    // 8. Insert money
    // (will ask for input — type: 10 first, then 15)
    System.out.println("\n--- Step 3: Insert Money ---");
    vm.insertMoney();

    // 9. Dispense
    System.out.println("\n--- Step 4: Dispense ---");
    vm.dispense();

    System.out.println("\n=== Transaction Complete ===");
  }
}