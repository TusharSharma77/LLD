import java.util.Objects;

public class User {
    private int userId;
    private String userName;
    private String email;
    private String phoneNumber;
    private BalanceSheet balancesheet;

    public User(int userId, String userName, String email, String phoneNumber) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.balancesheet = new BalanceSheet(this);
    }

    // getter and setter
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BalanceSheet getBalanceSheet() {
        return balancesheet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return userName + " (ID: " + userId + ")";
    }
}
