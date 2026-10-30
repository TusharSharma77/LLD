import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private String ticketNumber;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Double price;

    public Ticket(ParkingSpot parkingSpot, Vehicle vehicle, LocalDateTime entryTime) {
        this.ticketNumber = UUID.randomUUID().toString();
        this.parkingSpot = parkingSpot;
        this.vehicle = vehicle;
        this.entryTime = entryTime != null ? entryTime : LocalDateTime.now();     
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // getters
    public String getTicketNumber() {
        return ticketNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public Double getPrice() {
        return price;
    }
}
