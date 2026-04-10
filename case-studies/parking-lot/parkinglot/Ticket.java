package parkinglot;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

class Ticket {
  private String ticketId;
  private Vehicle vehicle;
  private ParkingSpot spot;
  private LocalDateTime entryTime;
  private LocalDateTime exitTime;
  private double amount;
  private static int defaultHours = 1; // default hours for minimum charge

  Ticket(Vehicle vehicle, ParkingSpot spot) {
    this.ticketId = UUID.randomUUID().toString();
    this.vehicle = vehicle;
    this.spot = spot;
    this.entryTime = LocalDateTime.now();
  }

  public void closeTicket() {
    this.exitTime = LocalDateTime.now();
    this.amount = calculateAmount();
  }

  private double calculateAmount() {
    long hours = ChronoUnit.HOURS.between(
      entryTime, exitTime) + defaultHours; // round up to next hour
    // different rates per vehicle type!
    return switch(vehicle.getVehicleType()) {
      case BIKE -> hours * 10;
//      case CAR   -> hours * 20;  default value same as this so commented out
      case TRUCK -> hours * 40;
      default    -> hours * 20;
    };
  }

  public String getTicketId() { return ticketId; }
  public ParkingSpot getSpot() { return spot; }
  public double getAmount() { return amount; }
}
