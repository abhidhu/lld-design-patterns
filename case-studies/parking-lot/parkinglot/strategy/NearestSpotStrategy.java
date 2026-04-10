package parkinglot.strategy;

import java.util.List;

import parkinglot.ParkingFloor;
import parkinglot.ParkingSpot;
import parkinglot.Vehicle;

public class NearestSpotStrategy implements ParkingStrategy {
  @Override
  public ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle) {

    // check floor by floor — nearest first!
    for (ParkingFloor floor : floors) {
      ParkingSpot spot = floor.getAvailableSpot(vehicle.getSpotType());
      if (spot != null) {
        return spot;
      }
    }
    return null; // no spot available!
  }
}
