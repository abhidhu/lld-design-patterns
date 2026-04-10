package parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingFloor {
  private int floorNumber;
  private List<ParkingSpot> parkingSpots;

  public ParkingFloor(int floorNumber){
    this.floorNumber = floorNumber;
    this.parkingSpots = new ArrayList<>();
  }

  public void addSpot(ParkingSpot spot) {
    parkingSpots.add(spot);
  }

  public ParkingFloor(int floorNumber, List<ParkingSpot> parkingSpots) {
    this.floorNumber = floorNumber;
    this.parkingSpots = new ArrayList<>();
    }

  // find available spot of required type
  public ParkingSpot getAvailableSpot(SpotType type) {
    return parkingSpots.stream()
      .filter(s -> s.getSpotType() == type)
      .filter(s -> s.isEmpty())
      .findFirst()
      .orElse(null);
  }

  public int getFloorNumber() { return floorNumber; }
  public List<ParkingSpot> getSpots() { return parkingSpots; }
}
