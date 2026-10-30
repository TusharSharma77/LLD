import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLotService {
    // activeTickets map to store the active tickets with ticket number as key and Ticket object as value
    private Map<String, Ticket> activeTickets;
    // parkingFloors list to store the parking floors in the parking lot
    private final List<ParkingFloor> parkingFloors;
    // pricingStrategy to calculate the price of parking based on the ticket
    private final PricingStrategy pricingStrategy;

    public ParkingLotService(List<ParkingFloor> parkingFloors, PricingStrategy pricingStrategy) {
        this.activeTickets = new ConcurrentHashMap<>();
        this.parkingFloors = parkingFloors;
        this.pricingStrategy = pricingStrategy;
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        for (ParkingFloor floor : parkingFloors) {
            for (ParkingSpot spot : floor.getParkingSpots()) {
                if (spot.getVehicleType() == vehicle.getVehicleType() && spot.isAvailable()) {
                    spot.parkVehicle(vehicle);
                    Ticket ticket = new Ticket(spot, vehicle, LocalDateTime.now());
                    activeTickets.put(ticket.getTicketNumber(), ticket);
                    return ticket;
                }
            }
        }
        throw new IllegalStateException("No available parking spot for vehicle type: " + vehicle.getVehicleType());
    }

    public Ticket unparkVehicle(String ticketNumber) {
        Ticket ticket = activeTickets.get(ticketNumber);
        if (ticket == null) {
            throw new IllegalArgumentException("Invalid ticket number: " + ticketNumber);
        }
        ParkingSpot spot = ticket.getParkingSpot();
        spot.removeVehicle();
        ticket.setExitTime(LocalDateTime.now());
        double price = pricingStrategy.calculatePrice(ticket);
        ticket.setPrice(price);
        activeTickets.remove(ticketNumber);
        return ticket;
    }
}
