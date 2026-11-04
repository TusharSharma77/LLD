import java.util.ArrayList;
import java.util.List;

public class ExactSplit implements SplitStrategy {
    @Override
    public List<Split> calculateSplits(double totalAmount, User paidBy, List<User> participants, List<Double> splitValues) {
        if (participants == null || splitValues == null || participants.size() != splitValues.size()) {
            throw new IllegalArgumentException("Number of participants must match number of split values.");
        }

        double sum = 0.0;
        for (double val : splitValues) {
            sum += val;
        }

        if (Math.abs(sum - totalAmount) > 0.01) {
            throw new IllegalArgumentException("Sum of exact splits ($" + sum + ") does not equal total amount ($" + totalAmount + ").");
        }

        List<Split> splits = new ArrayList<>();
        for (int i = 0; i < participants.size(); i++) {
            splits.add(new Split(participants.get(i), splitValues.get(i)));
        }
        return splits;
    }
}
