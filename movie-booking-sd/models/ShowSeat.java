public class ShowSeat {
    private String id;
    private SeatType seatType;
    private Seat seat;
    private SeatStatus seatStatus;
    private double price;

    public ShowSeat(String id, SeatType seatType, Seat seat, SeatStatus seatStatus, double price) {
        this.id = id;
        this.seatType = seatType;
        this.seat = seat;
        this.seatStatus = seatStatus;
        this.price = price;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public SeatStatus getStatus() {
        return seatStatus;
    }

    public void setStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
