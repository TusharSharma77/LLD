public class ParkingLotMain {
    private ParkingLotService parkingLotService;

    public ParkingLotMain(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        return parkingLotService.parkVehicle(vehicle);
    }

    public Ticket unparkVehicle(String ticketNumber) {
        return parkingLotService.unparkVehicle(ticketNumber);
    }
}
