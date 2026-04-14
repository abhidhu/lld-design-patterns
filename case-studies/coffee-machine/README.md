# Coffee Machine — Low Level Design (LLD)

## Problem Statement
Design a Coffee Machine that:
- Makes different coffees (Latte, Americano, Espresso)
- Supports extras/add-ons (Sugar, Milk)
- Only one machine exists in the system
- Hides internal coffee creation from the user

---

## Design Patterns Used

| Pattern | Where Used | Why |
|---|---|---|
| Singleton | `CoffeeMachine` | Only one machine should exist |
| Factory | `CoffeeFactory` | Hides object creation logic from caller |
| Decorator | `SugarCoffee`, `MilkCoffee` | Add extras without changing base class |

> **Note:** Strategy pattern was intentionally removed.
> Latte/Americano as Coffee subclasses provides cleaner abstraction.
> Brewing algorithm is implicitly handled through polymorphism.

---

## Class Structure

```
CoffeeMachine (Singleton)
    └── CoffeeFactory
            └── Map<CoffeeType, Supplier<Coffee>>
                    ├── Latte extends Coffee
                    ├── Americano extends Coffee
                    └── Espresso extends Coffee

Decorators (wrap Coffee object):
    ├── SugarCoffee extends Coffee + HAS-A Coffee
    └── MilkCoffee extends Coffee + HAS-A Coffee
```

---

## Relationships

- `Latte` **IS-A** `Coffee` → Inheritance
- `SugarCoffee` **IS-A** `Coffee` + **HAS-A** `Coffee` → Decorator Pattern
- `CoffeeMachine` **HAS-A** `CoffeeFactory` → Composition
- `CoffeeFactory` **HAS-A** `Map of Suppliers` → Factory Pattern

---

## Flow

```
User → CoffeeMachine.getInstance()
     → makeCoffee(CoffeeType, List<Decorators>)
     → CoffeeFactory creates base Coffee via Supplier
     → Decorators wrap Coffee one by one
     → Final Coffee returned to user
```

---

## Usage

```java
CoffeeMachine machine = CoffeeMachine.getInstance();

// Plain Latte
Coffee latte = machine.makeCoffee(CoffeeType.LATTE, List.of());

// Americano with Sugar and Milk
Coffee custom = machine.makeCoffee(
    CoffeeType.AMERICANO,
    List.of(CoffeeDecorators.SUGAR, CoffeeDecorators.MILK)
);

System.out.println(custom.getDescription()); // Americano coffee with sugar with milk
System.out.println(custom.getPrice());       // 5.0 + 2.0 + 5.0 = 12.0
```

---

## Key Design Decisions

**Why Supplier in Factory map?**
> Storing `new Latte()` directly reuses same object for every request.
> `Supplier<Coffee>` creates fresh object every call — no shared state!

**Why remove Strategy pattern?**
> Each coffee type (Latte, Americano) is a product, not a process.
> Polymorphism handles different behaviour cleanly.
> Avoids unnecessary complexity.

**Why Decorator for extras?**
> Extras (Sugar, Milk) wrap the base coffee and add cost/description.
> New extras can be added without modifying existing classes — Open/Closed Principle! ✅

---

## Adding New Coffee Type
1. Create new class extending `Coffee`
2. Add to `CoffeeType` enum
3. Register in `CoffeeFactory` static block

```java
coffeeTypeCoffeeMap.put(CoffeeType.ESPRESSO, Espresso::new);
```

No existing code modified! ✅ Open/Closed Principle maintained!

---

## Adding New Decorator
1. Create new class extending `Coffee` with `HAS-A Coffee`
2. Add to `CoffeeDecorators` enum
3. Add case in `CoffeeFactory` switch

---

## What I Learned
- Pattern recognition through problem decomposition
- When NOT to use a pattern (Strategy removed!)
- Supplier functional interface for fresh object creation
- Decorator = IS-A + HAS-A on same class
- Singleton with reflection protection
