import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingService {
    private final SeatLockProvider seatLockProvider;
    private final PaymentService paymentService;
    private final PricingStrategy pricingStrategy;

    public BookingService(SeatLockProvider seatLockProvider, PaymentService paymentService, PricingStrategy pricingStrategy) {
        this.seatLockProvider = seatLockProvider;
        this.paymentService = paymentService;
        this.pricingStrategy = pricingStrategy;
    }

    public Booking createBooking(User user, Show show, List<ShowSeat> seats) {
        // 1. Validate that all seats are currently available
        for (ShowSeat showSeat : seats) {
            if (showSeat.getSeatStatus() != SeatStatus.AVAILABLE) {
                throw new IllegalArgumentException("Seat " + showSeat.getSeat().getId() + " is not available for booking.");
            }
        }

        // 2. Extract domain Seats and attempt temporary locking
        List<Seat> seatList = new ArrayList<>();
        for (ShowSeat showSeat : seats) {
            seatList.add(showSeat.getSeat());
        }
        seatLockProvider.lockSeats(show, seatList, user);

        // 3. Mark show seats as temporarily locked
        for (ShowSeat showSeat : seats) {
            showSeat.setSeatStatus(SeatStatus.LOCKED);
        }

        // 4. Calculate total amount using dynamic pricing strategy
        double totalAmount = pricingStrategy.calculatePrice(seats);

        // 5. Create and return pending booking
        return new Booking(
                UUID.randomUUID().toString(),
                user,
                show,
                seats,
                BookingStatus.PENDING,
                totalAmount,
                Instant.now()
        );
    }

    public Booking confirmBooking(Booking booking) {
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException("Booking is not in a pending state.");
        }

        List<Seat> seatList = new ArrayList<>();
        for (ShowSeat showSeat : booking.getBookedSeats()) {
            seatList.add(showSeat.getSeat());
        }

        // 1. Process payment
        boolean paymentSuccess = paymentService.processPayment(booking.getUser(), booking.getTotalAmount());

        if (paymentSuccess) {
            booking.setStatus(BookingStatus.CONFIRMED);
            // 2. Mark show seats permanently as BOOKED
            for (ShowSeat showSeat : booking.getBookedSeats()) {
                showSeat.setSeatStatus(SeatStatus.BOOKED);
            }
            // 3. Release temporary locks as seats are now booked
            seatLockProvider.unlockSeats(booking.getShow(), seatList, booking.getUser());
        } else {
            booking.setStatus(BookingStatus.CANCELLED);
            // Revert seat status back to AVAILABLE
            for (ShowSeat showSeat : booking.getBookedSeats()) {
                showSeat.setSeatStatus(SeatStatus.AVAILABLE);
            }
            // Unlock seats so other users can book them
            seatLockProvider.unlockSeats(booking.getShow(), seatList, booking.getUser());
        }

        return booking;
    }
}
