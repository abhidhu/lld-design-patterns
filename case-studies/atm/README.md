# ATM Machine

A complete Low Level Design implementation of an ATM Machine in plain Java, demonstrating real-world design patterns and SOLID principles.

---

## Problem Statement

Design an ATM Machine where:
- A user can insert a card, authenticate with a PIN, select an account and perform transactions
- ATM enforces per-account daily withdrawal limits and overdraft rules
- Cash dispenser tracks available cash and rejects transactions when insufficient
- ATM transitions through well-defined states — any invalid action in a given state is rejected automatically
- Receipt and transaction log are generated after every successful transaction

---

## Functional Requirements

- User can insert a card (expired cards rejected)
- User can authenticate using PIN
- User can select from multiple accounts linked to one card
- User can withdraw cash (daily limit + overdraft rules enforced)
- User can deposit cash
- User can eject card / cancel at any point
- ATM transitions to OUT_OF_SERVICE when cash runs out
- Receipt is printed and transaction is logged after every successful transaction

## Non-Functional Requirements

- ATM is a single global instance (Singleton)
- Balance operations are thread-safe
- Daily withdrawal limit resets automatically at midnight
- Notification channels (SMS, WhatsApp) are pluggable without modifying existing code
- Adding a new state or transaction observer requires no changes to existing classes

---

## Design Decisions

### Why abstract class for ATMState instead of interface?
All action methods (insertCard, enterPIN, withdraw, etc.) default to throwing `IllegalStateException`. An abstract class provides this default behaviour for free. An interface would force every concrete state to implement every method with empty stubs, which is boilerplate noise.

### Why enum for AccountType carries limits?
Daily withdrawal limit and overdraft limit belong to the account type, not individual accounts. Putting them in `AccountType` enum means changing a limit in one place propagates everywhere with no risk of inconsistency.

### Why debit first, then dispense (compensating transaction)?
Locking the balance during dispense wastes a CPU thread waiting for a mechanical operation. Instead: debit the account, attempt dispense, and if dispense fails refund via `addBalance()`. This is the compensating transaction pattern — no locks, no blocked threads.

### Why Observer only for receipt/logging, not for cash shortage?
Observer is fire-and-forget — the emitter does not care who is listening. Receipt printing and logging fit this perfectly. But ATM must react immediately and knowingly to a cash shortage (switching state to OUT_OF_SERVICE). That requires a direct call, not a notification.

### Why State pattern instead of if/switch in ATM?
Each state owns exactly the actions valid in that state. Invalid actions auto-throw without any conditional logic in ATM. Adding a new state means writing a new class — ATM itself never changes.

### Why SRP pushes transaction logic into TransactionService?
States manage transitions only. Business rules (daily limit check, overdraft check, dispense-then-refund) live in `TransactionService` so neither the state classes nor `Account` become dumping grounds.

---

## Design Patterns Used

| Pattern | Where Used | Why |
| --- | --- | --- |
| State | ATMState + 5 concrete states | Eliminates if/switch in ATM, invalid actions auto-throw |
| Observer | TransactionObserver, ReceiptPrinter, TransactionLogger | Fire-and-forget notification after transaction — ATM doesn't care who listens |
| Singleton | ATM (eager init) | One ATM instance, globally accessible |
| Strategy | NotificationStrategy (extensible) | Plug in SMS/WhatsApp/Email without touching existing classes |
| Compensating Transaction | TransactionService.withdraw() | Debit first, refund on dispense failure — no locks needed |

---

## SOLID Principles Applied

| Principle | Where Applied |
| --- | --- |
| Single Responsibility | States only manage transitions, TransactionService owns all transaction business rules |
| Open/Closed | New state = new class, new observer = new class, no existing code changes |
| Liskov Substitution | Any ATMState subclass can replace another; any TransactionObserver can replace another |
| Interface Segregation | TransactionObserver is a single-method interface — observers only implement what they need |
| Dependency Inversion | TransactionService depends on TransactionObserver interface, not ReceiptPrinter or TransactionLogger directly |

---

## Class Structure

```
src/
  enums/
    AccountType         (dailyWithdrawLimit, overdraftLimit per type)
    ATMStateType        (user-facing message per state)
    TransactionType

  account/
    Account (abstract)  (withdraw with daily limit + overdraft + midnight reset)
    SavingAccount
    CurrentAccount
    Card                (cardNo, expiryDate, List<Account>, isExpired())

  state/
    ATMState (abstract) (all actions throw by default)
    IdleState           (insertCard)
    CardInsertedState   (enterPIN, ejectCard)
    AuthenticatedState  (selectAccount, ejectCard)
    TransactionState    (withdraw, deposit, ejectCard)
    OutOfServiceState   (nothing — ATM unavailable)

  transaction/
    Transaction
    TransactionType

  observer/
    TransactionObserver (interface)
    ReceiptPrinter
    TransactionLogger

  dispenser/
    CashDispenser       (hasSufficientCash, dispense, isEmpty)

  service/
    CardAuthService     (registerCard, authenticate with hashed PIN)
    TransactionService  (withdraw with compensating tx, deposit, notifyObservers)

  atm/
    ATM (Singleton)     (wires all components, delegates actions to current state)

  Main
```

---

## Key Flows

### Normal Withdrawal Flow
```
insertCard()     → IdleState       → validates expiry → CardInsertedState
enterPIN()       → CardInsertedState → CardAuthService.authenticate() → AuthenticatedState
selectAccount()  → AuthenticatedState → ATM.setSelectedAccount() → TransactionState
withdraw()       → TransactionState → TransactionService.withdraw()
                     → account.withdraw()     (debit — daily limit + overdraft checked)
                     → dispenser.dispense()   (mechanical)
                     → notifyObservers()      (ReceiptPrinter + TransactionLogger)
ejectCard()      → TransactionState → reset card + account → IdleState
```

### Dispense Failure Flow (Compensating Transaction)
```
account.withdraw(amount)       ← debit succeeds
dispenser.dispense(amount)     ← throws (insufficient cash)
account.addBalance(amount)     ← refund immediately
dispenser.isEmpty()?           ← yes → atm.setState(OutOfServiceState)
```

### Invalid Action Flow
```
atm.withdraw(1000)  ← called while in IdleState
ATMState.withdraw() ← default throws IllegalStateException("Please insert your card")
```

---

## Sample Output

```
=== Normal withdrawal flow ===
Card inserted. Please enter your PIN
PIN verified. Please select an account
Account selected. Choose a transaction
Dispensing ₹5000.0 | Remaining in ATM: ₹195000.0
---- RECEIPT ----
Transaction ID : 38f9e051-9d6e-4382-bf2e-ac28bad03c8a
Account        : ACC001
Type           : WITHDRAWAL
Amount         : ₹5000.0
Time           : 2026-05-19T23:02:04.407555500
-----------------
[LOG] 2026-05-19T23:02:04 | WITHDRAWAL | Account: ACC001 | Amount: ₹5000.0 | TxnId: 38f9e051...
Withdrew ₹5000.0 successfully
Card ejected. Please insert your card

=== Wrong PIN attempt ===
Card inserted. Please enter your PIN
Caught: Invalid PIN
Card ejected. Please insert your card

=== Invalid action (withdraw before inserting card) ===
Caught: Invalid action: Please insert your card

=== Overdraft on savings ===
Card inserted. Please enter your PIN
PIN verified. Please select an account
Account selected. Choose a transaction
Caught: Daily withdrawal limit of ₹40000.0 exceeded
Card ejected. Please insert your card
```

---

## How to Run

1. Clone the repository
2. Open `case-studies/atm` in IntelliJ IDEA
3. Run `Main.java`
4. JDK 21+ recommended

---

## What I Learned

- How State pattern eliminates conditionals — each state is self-contained and invalid actions fail automatically
- Why Observer is fire-and-forget — if ATM cares about the outcome, use a direct call instead
- Compensating transaction pattern — debit first, refund on failure, no locks needed
- Why limits belong in the enum not the class — one place to change, no inconsistency risk
- How SRP keeps states thin and services testable independently
