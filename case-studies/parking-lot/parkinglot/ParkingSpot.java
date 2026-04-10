package parkinglot;

public class ParkingSpot {
  private final String ParkingSpotId;
  private final SpotType spotType;
  private boolean isEmpty;
  private Vehicle vehicle;

  public ParkingSpot(String parkingSpotId, SpotType spotType) {
    ParkingSpotId = parkingSpotId;
    this.spotType = spotType;
    this.isEmpty = true;
    this.vehicle = null;
  }

  public boolean isEmpty() {
    return this.isEmpty;
  }

  public String getParkingSpotId() {
    return ParkingSpotId;
  }

  public SpotType getSpotType() {
    return spotType;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public boolean park(Vehicle vehicle) {
    if (!isEmpty) {
      return false;
    }
    if (vehicle.getSpotType() != this.spotType) {
      return false;
    }
    this.vehicle = vehicle;
    this.isEmpty = false;
    return true;
  }

  public void removeVehicle() {
    this.vehicle = null;
    this.isEmpty = true;
  }
}
