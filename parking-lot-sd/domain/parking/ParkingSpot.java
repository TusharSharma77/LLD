public class ParkingSpot {
    private String spotId;
    private VehicleType vehicleType;

    // if parked then its information also
    private Vehicle vehicle;

    public ParkingSpot(String spotId, VehicleType vehicleType) {
        this.spotId = spotId;
        this.vehicleType = vehicleType;
    }

    // getters
    public String getSpotId() {
        return spotId;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    // if vehicle is not parked yet
    public boolean isAvailable() {
        return vehicle == null;
    }

    // synchronized because multiple threads can try to park at same time
    public synchronized void parkVehicle(Vehicle vehicle) {
        if (vehicle.getVehicleType() != this.vehicleType) {
            throw new IllegalArgumentException("Vehicle type does not match parking spot type");
        }
        if (this.vehicle != null) {
            throw new IllegalStateException("Parking spot is already occupied");
        }
        this.vehicle = vehicle;
    }

    public synchronized void removeVehicle() {
        this.vehicle = null;
    }
}
