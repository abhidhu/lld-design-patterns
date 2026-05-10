# Vending Machine

A complete Low Level Design implementation of a Vending Machine in plain Java, demonstrating real-world design patterns and SOLID principles.

---

## Problem Statement

Design a Vending Machine where:
- Users can browse available products and select one to purchase
- Users insert money until the full price is met; excess is returned as change
- The machine dispenses the selected product and updates its inventory
- Admins are notified when inventory falls below a threshold

---

## Functional Requirements

- Display all available products with price and serial number
- User selects a product by serial number
- User inserts money in one or more steps until amount is met
- Machine dispenses the product and returns change
- User can cancel an order and receive a refund at any point
- Admin is notified via SMS or Email when inventory is low
- Admin can be registered with a preferred notification strategy

## Non-Functional Requirements

- Only one VendingMachine instance should exist (Singleton)
- New product types must be addable without modifying existing code (Open/Closed)
- Notification channels must be pluggable (SMS, Email)
- State transitions must be strict — invalid actions at the wrong state are rejected

---

## Design Decisions

### Why State Pattern for machine flow?
A vending machine has strict sequential steps: Idle → ProductSelected → MoneyInserted → Dispensing. The State pattern encodes this as separate classes so each state only handles valid actions and rejects the rest, making illegal transitions impossible by design.

### Why Observer for admin notifications?
Inventory events are internal to the machine, but admins need to be aware of them without coupling the machine to admin-specific logic. Observer decouples the notification trigger (machine) from the notification handler (admin), allowing multiple admins to be registered and removed at runtime.

### Why Strategy for notifications?
Each admin can prefer a different notification channel (SMS vs Email). Strategy allows the notification behaviour to be swapped at runtime per admin without conditional logic scattered across the codebase.

### Why Factory for products?
Product creation requires mapping a string type to a concrete class. Factory centralises this mapping so callers never import or instantiate concrete product classes directly.

### Why Singleton is reflection-safe?
The `INSTANCE` field is eagerly initialised before any external code runs. If reflection is used to call the private constructor, `INSTANCE` is already non-null at that point, so the guard throws immediately. This is the standard eager-init reflection defence.

---

## Design Patterns Used

| Pattern | Where Used | Why |
| --- | --- | --- |
| State | `MachineState`, `IdleState`, `ProductSelectedState`, `MoneyInsertedState`, `DispensingState` | Encodes strict transition rules and prevents invalid actions per state |
| Observer | `Subject`, `Observer`, `VendingMachine`, `Admin` | Decouples inventory events from admin notification logic |
| Strategy | `NotificationStrategy`, `SMSNotification`, `EmailNotification` | Swappable notification channel per admin without conditional logic |
| Factory | `ProductFactory` | Centralises product creation and hides concrete subclasses from callers |
| Singleton | `VendingMachine` | One machine instance, reflection-safe via eager init guard |

---

## SOLID Principles Applied

| Principle | Where Applied |
| --- | --- |
| Single Responsibility | Each state class handles only its own valid transitions; `Admin` only handles notifications |
| Open/Closed | New product types extend `Product`; new notification channels implement `NotificationStrategy` — no existing code changes |
| Liskov Substitution | Any `MachineState` implementation can replace another without breaking the machine |
| Interface Segregation | `Observer` and `Subject` are separate focused interfaces |
| Dependency Inversion | `Admin` depends on `NotificationStrategy` interface, not on `SMSNotification` or `EmailNotification` directly |

---

## Class Structure

```
src/
  vendingmachine/
    VendingMachine.java       (Singleton + Subject)
    Main.java

  state/
    MachineState.java         (abstract base state)
    IdleState.java
    ProductSelectedState.java
    MoneyInsertedState.java
    DispensingState.java

  product/
    Product.java              (abstract)
    Chips.java
    Chocolate.java
    Water.java
    ProductFactory.java
    Slot.java

  order/
    Order.java

  domain/
    Admin.java                (Observer)

  observer/
    Observer.java             (interface)
    Subject.java              (interface)

  strategy/
    NotificationStrategy.java (interface)
    SMSNotification.java
    EmailNotification.java

  enums/
    MachineStateEnum.java
    SlotSize.java
    OrderStatus.java
    NotificationStrategyEnum.java
```

---

## State Machine

```
           showProducts()
[IDLE] ─────────────────────► [PRODUCT_SELECTED]
                                      │
                              selectProduct()
                                      │
                                      ▼
                              [MONEY_INSERTED] ◄─── insertMoney() (insufficient)
                                      │
                              insertMoney() (sufficient)
                                      │
                                      ▼
                               [DISPENSING]
                                      │
                               dispense()
                                      │
                                      ▼
                                   [IDLE]

cancel() is valid from PRODUCT_SELECTED and MONEY_INSERTED → returns to IDLE
```

---

## Key Flows

### Purchase Flow
```
User → vm.showProducts()
     → IdleState displays products → transitions to ProductSelectedState

User → vm.selectProduct()  (enters serial number)
     → ProductSelectedState creates Order → transitions to MoneyInsertedState

User → vm.insertMoney()  (enters amount, repeat until paid)
     → MoneyInsertedState accumulates payment
     → when paid: calculates change → transitions to DispensingState

User → vm.dispense()
     → DispensingState marks order COMPLETED
     → removes product from slot
     → transitions to IdleState
     → checkInventory() → notifies admins if low
```

### Cancel Flow
```
User → vm.cancel()  (from ProductSelectedState or MoneyInsertedState)
     → marks order CANCELLED
     → refunds amount paid (if any)
     → transitions to IdleState
```

---

## Sample Output

```
=== Vending Machine Started ===

--- Step 1: Show Products ---
Product id 1 product name: Lays Classic - $20.0
Product id 2 product name: Bisleri - $15.0
Product id 3 product name: Dairy Milk - $30.0

--- Step 2: Select Product (enter serialNo) ---
1
Selected product: Lays Classic - $20.0

--- Step 3: Insert Money ---
20
Payment successful. Change: $0.0

--- Step 4: Dispense ---
Dispensing product...
Product dispensed. Thank you for your purchase!

=== Transaction Complete ===
```

---

## How to Run

1. Clone the repository
2. Open `case-studies/vending-machine` in IntelliJ IDEA or any Java IDE
3. Run `Main.java`
4. JDK 17+ recommended (uses switch expressions)
5. Follow the prompts — enter a serial number when asked, then enter the payment amount

---

## What I Learned

- How the State pattern makes illegal state transitions impossible by construction
- How Observer and Strategy compose naturally — Observer triggers the event, Strategy decides how to handle it
- Why eager Singleton initialisation provides reflection safety that lazy init does not
- How Factory hides subtype details and keeps the caller's import surface clean
- Difference between `amountDue` (what the machine is owed) and `change` (what the machine owes back)
