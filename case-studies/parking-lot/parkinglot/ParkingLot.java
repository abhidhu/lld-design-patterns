package parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import parkinglot.strategy.NearestSpotStrategy;
import parkinglot.strategy.ParkingStrategy;

class ParkingLot {
  // Singleton!
  private static ParkingLot instance;

  private List<ParkingFloor> floors;
  private ParkingStrategy strategy;
  private Map<String, Ticket> activeTickets;

  // private constructor!
  private ParkingLot() {
    floors = new ArrayList<>();
    activeTickets = new HashMap<>();
    strategy = new NearestSpotStrategy(); // default!
  }

  public static ParkingLot getInstance() {
    if (instance == null) {
      synchronized (ParkingLot.class) {
        if (instance == null) {
          instance = new ParkingLot();
        }
      }
    }
    return instance;
  }

  // change strategy at runtime!
  public void setStrategy(ParkingStrategy strategy) {
    this.strategy = strategy;
  }

  public void addFloor(ParkingFloor floor) {
    floors.add(floor);
  }

  // Entry — park vehicle
  public Ticket parkVehicle(Vehicle vehicle) {
    ParkingSpot spot = strategy.findSpot(floors, vehicle);

    if (spot == null) {
      throw new RuntimeException("No spot available!");
    }

    spot.park(vehicle);
    Ticket ticket = new Ticket(vehicle, spot);
    activeTickets.put(ticket.getTicketId(), ticket);
    return ticket;
  }

  // Exit — remove vehicle
  public double exitVehicle(String ticketId) {
    Ticket ticket = activeTickets.get(ticketId);
    if (ticket == null) {
      throw new RuntimeException("Invalid ticket!");
    }

    ticket.closeTicket();
    ticket.getSpot().removeVehicle();
    activeTickets.remove(ticketId);
    return ticket.getAmount();
  }
}