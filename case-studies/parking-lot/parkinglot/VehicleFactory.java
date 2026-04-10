package parkinglot;

/**
 * Factory class to create instances of different types of vehicles.
 */
public class VehicleFactory {

  public static Vehicle createVehicle(VehicleType type, String numberPlate) {
    switch (type) {
      case CAR:
        return new Car(numberPlate);
      case BIKE:
        return new Bike(numberPlate);
      case TRUCK:
        return new Truck(numberPlate);
      default:
        throw new IllegalArgumentException("Invalid vehicle type: " + type);
    }
  }
}
