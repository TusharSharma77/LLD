import java.util.ArrayList;
import java.util.List;

public class MovieBookingMain {
    public static void main(String[] args) {
        System.out.println("=== Movie Booking System (BookMyShow / LLD) ===");

        // 1. Setup Cinema & Screen
        Cinema cinema = new Cinema("PVR Superplex", "C1", "Bangalore");
        Screen screen1 = new Screen("S1", "Audi-1");
        cinema.addScreen(screen1);

        // 2. Add Physical Seats to Screen
        Seat seat1 = new Seat("A1", SeatType.REGULAR, 1, 1);
        Seat seat2 = new Seat("A2", SeatType.REGULAR, 1, 2);
        Seat seat3 = new Seat("B1", SeatType.PREMIUM, 2, 1);
        screen1.addSeat(seat1);
        screen1.addSeat(seat2);
        screen1.addSeat(seat3);

        // 3. Setup Movie & Show
        Movie movie = new Movie("M1", "Inception", "Sci-Fi Thriller", "148");
        long startTime = System.currentTimeMillis() + 3600000;
        long endTime = startTime + 7200000;
        Show show = new Show("SHOW101", movie, screen1, startTime, endTime);

        // 4. Create ShowSeats for this specific show
        ShowSeat showSeat1 = new ShowSeat("SS1", SeatType.REGULAR, seat1, SeatStatus.AVAILABLE, 250.0);
        ShowSeat showSeat2 = new ShowSeat("SS2", SeatType.REGULAR, seat2, SeatStatus.AVAILABLE, 250.0);
        ShowSeat showSeat3 = new ShowSeat("SS3", SeatType.PREMIUM, seat3, SeatStatus.AVAILABLE, 400.0);

        // 5. Initialize Services & Providers
        SeatLockProvider seatLockProvider = new InMemorySeatLockProvider(300); // 5 min lock
        PaymentService paymentService = new PaymentService();
        PricingStrategy pricingStrategy = new DefaultPricingStrategy();
        BookingService bookingService = new BookingService(seatLockProvider, paymentService, pricingStrategy);

        // 6. User 1 tries to book seats A1 and A2
        User user1 = new User("U1", "Alice", "alice@example.com", "9876543210");
        List<ShowSeat> selectedSeatsUser1 = List.of(showSeat1, showSeat2);

        System.out.println("\n--- Step 1: User 1 creates a booking for [A1, A2] ---");
        Booking bookingUser1 = bookingService.createBooking(user1, show, selectedSeatsUser1);
        System.out.println("Booking Created! ID: " + bookingUser1.getId());
        System.out.println("Status: " + bookingUser1.getStatus());
        System.out.println("Total Amount: INR " + bookingUser1.getTotalAmount());
        System.out.println("Seats Status after lock: A1=" + showSeat1.getSeatStatus() + ", A2=" + showSeat2.getSeatStatus());

        // 7. User 2 concurrently tries to book seat A1 (should fail due to lock)
        User user2 = new User("U2", "Bob", "bob@example.com", "9123456789");
        List<ShowSeat> selectedSeatsUser2 = List.of(showSeat1, showSeat3);

        System.out.println("\n--- Step 2: User 2 attempts to lock [A1, B1] ---");
        try {
            bookingService.createBooking(user2, show, selectedSeatsUser2);
            System.out.println("User 2 successfully booked seats (Unexpected!)");
        } catch (Exception e) {
            System.out.println("Locking Rejected as expected: " + e.getMessage());
        }

        // 8. User 1 completes payment and confirms booking
        System.out.println("\n--- Step 3: User 1 confirms payment ---");
        Booking confirmedBooking = bookingService.confirmBooking(bookingUser1);
        System.out.println("Booking Status: " + confirmedBooking.getStatus());
        System.out.println("Seat A1 Status: " + showSeat1.getSeatStatus());
        System.out.println("Seat A2 Status: " + showSeat2.getSeatStatus());
    }
}
