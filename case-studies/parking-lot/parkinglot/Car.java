package parkinglot;

public class Car extends Vehicle {

  public Car(String numberPlate) {
    super(numberPlate, VehicleType.CAR);
  }
    @Override
    public SpotType getSpotType() {
        return SpotType.MEDIUM;
    }
}
