package parkinglot.strategy;

import java.util.List;

import parkinglot.ParkingFloor;
import parkinglot.ParkingSpot;
import parkinglot.Vehicle;

public interface ParkingStrategy {
  ParkingSpot findSpot(List<ParkingFloor> parkingFloors, Vehicle vehicle);
}
