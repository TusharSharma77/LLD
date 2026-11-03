import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemorySeatLockProvider implements SeatLockProvider {
    private final Map<Show, Map<Seat, SeatLock>> seatLocks = new ConcurrentHashMap<>();
    private final long lockTimeoutinSeconds;

    public InMemorySeatLockProvider() {
        this(600); // default 10 minutes timeout
    }

    public InMemorySeatLockProvider(long lockTimeoutinSeconds) {
        this.lockTimeoutinSeconds = lockTimeoutinSeconds;
    }

    @Override
    public synchronized void lockSeats(Show show, List<Seat> seats, User user) {
        Map<Seat, SeatLock> showLocks = seatLocks.computeIfAbsent(show, s -> new ConcurrentHashMap<>());
        
        // 1. Verify all requested seats are available to lock
        for (Seat seat : seats) {
            SeatLock existingLock = showLocks.get(seat);
            if (existingLock != null && !existingLock.isExpired() && !existingLock.getLockedbyUserId().equals(user.getId())) {
                throw new IllegalStateException("Seat " + seat.getId() + " is already locked by another user.");
            }
        }

        // 2. Lock all seats atomically for this user
        long now = System.currentTimeMillis();
        for (Seat seat : seats) {
            SeatLock lock = new SeatLock(seat.getId(), show.getId(), now, user.getId(), lockTimeoutinSeconds);
            showLocks.put(seat, lock);
        }
    }

    @Override
    public synchronized void unlockSeats(Show show, List<Seat> seats, User user) {
        Map<Seat, SeatLock> showLocks = seatLocks.get(show);
        if (showLocks != null) {
            for (Seat seat : seats) {
                SeatLock existingLock = showLocks.get(seat);
                if (existingLock != null && (existingLock.getLockedbyUserId().equals(user.getId()) || existingLock.isExpired())) {
                    showLocks.remove(seat);
                }
            }
        }
    }

    @Override
    public synchronized boolean isSeatLocked(Show show, List<Seat> seats, User user) {
        Map<Seat, SeatLock> showLocks = seatLocks.get(show);
        if (showLocks == null) {
            return false;
        }
        for (Seat seat : seats) {
            SeatLock lock = showLocks.get(seat);
            if (lock != null && !lock.isExpired() && !lock.getLockedbyUserId().equals(user.getId())) {
                return true;
            }
        }
        return false;
    }
}
