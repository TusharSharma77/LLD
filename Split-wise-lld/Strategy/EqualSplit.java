import java.util.ArrayList;
import java.util.List;

public class EqualSplit implements SplitStrategy {
    @Override
    public List<Split> calculateSplits(double totalAmount, User paidBy, List<User> participants, List<Double> splitValues) {
        if (participants == null || participants.isEmpty()) {
            throw new IllegalArgumentException("Participants list cannot be empty");
        }
        List<Split> splits = new ArrayList<>();
        double amountPerPerson = Math.round((totalAmount / participants.size()) * 100.0) / 100.0;
        
        for (User participant : participants) {
            splits.add(new Split(participant, amountPerPerson));
        }
        return splits;
    }
}
