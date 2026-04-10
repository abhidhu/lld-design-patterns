package parkinglot;

public class Truck extends Vehicle {

    public Truck(String numberPlate) {
        super(numberPlate, VehicleType.TRUCK);
    }
    @Override
    public SpotType getSpotType() {
        return SpotType.LARGE;
    }
}
