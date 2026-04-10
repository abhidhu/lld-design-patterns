package parkinglot;

public class Bike extends Vehicle {

    public Bike(String numberPlate) {
        super(numberPlate, VehicleType.BIKE);
    }
    @Override
    public SpotType getSpotType() {
        return SpotType.SMALL;
    }
}
