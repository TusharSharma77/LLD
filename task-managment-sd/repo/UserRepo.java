import java.util.Optional;

public interface UserRepo {
    void save(User user);
    Optional<User> findById(String userId);
}