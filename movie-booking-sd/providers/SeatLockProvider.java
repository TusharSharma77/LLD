import java.util.List;

public interface SeatLockProvider {
    void lockSeats(Show show, List<Seat> seats, User user);
    void unlockSeats(Show show, List<Seat> seats, User user);
    boolean isSeatLocked(Show show, List<Seat> seats, User user);
}
