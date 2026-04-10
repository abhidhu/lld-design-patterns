package parkinglot;

public class Main {
  public static void main(String[] args) {
    // Setup parking lot
    ParkingLot lot = ParkingLot.getInstance();

    // Add floors and spots
    ParkingFloor floor1 = new ParkingFloor(1);
    floor1.addSpot(new ParkingSpot("F1-S1", SpotType.SMALL));
    floor1.addSpot(new ParkingSpot("F1-S2", SpotType.MEDIUM));
    floor1.addSpot(new ParkingSpot("F1-S3", SpotType.LARGE));
    lot.addFloor(floor1);

    // Park vehicles
    Vehicle car = VehicleFactory.createVehicle(VehicleType.CAR, "MH12AB1234");
    Ticket ticket = lot.parkVehicle(car);
    System.out.println("Parked! Ticket: " + ticket.getTicketId());

    // Exit
    double amount = lot.exitVehicle(ticket.getTicketId());
    System.out.println("Amount: Rs." + amount);
  }
}