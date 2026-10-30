import java.util.List;

public class ParkingFloor {
    private final String floorId;
    private final List<ParkingSpot> parkingSpots;

    public ParkingFloor(String floorId, List<ParkingSpot> parkingSpots) {
        this.floorId = floorId;
        this.parkingSpots = parkingSpots;
    }

    // getters
    public String getFloorId() {
        return floorId;
    }

    public List<ParkingSpot> getParkingSpots() {
        // return a copy of the list to prevent external modification
        return List.copyOf(parkingSpots);
    }
}
