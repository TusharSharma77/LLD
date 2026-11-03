public class SeatLock {
    private String seatId;
    private String showId;
    private long lockTime;
    private String lockedbyUserId;
    private long timeoutinseconds;

    public SeatLock(String seatId, String showId, long lockTime, String lockedbyUserId, long timeoutinseconds) {
        this.seatId = seatId;
        this.showId = showId;
        this.lockTime = lockTime;
        this.lockedbyUserId = lockedbyUserId;
        this.timeoutinseconds = timeoutinseconds;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > (lockTime + (timeoutinseconds * 1000));
    }

    // Getters and setters
    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public long getLockTime() {
        return lockTime;
    }

    public void setLockTime(long lockTime) {
        this.lockTime = lockTime;
    }

    public String getLockedbyUserId() {
        return lockedbyUserId;
    }

    public void setLockedbyUserId(String lockedbyUserId) {
        this.lockedbyUserId = lockedbyUserId;
    }

    public String getUserId() {
        return lockedbyUserId;
    }

    public void setUserId(String userId) {
        this.lockedbyUserId = userId;
    }

    public long getTimeoutinseconds() {
        return timeoutinseconds;
    }

    public void setTimeoutinseconds(long timeoutinseconds) {
        this.timeoutinseconds = timeoutinseconds;
    }
}
