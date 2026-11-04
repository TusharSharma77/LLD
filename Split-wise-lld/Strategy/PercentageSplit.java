import java.util.ArrayList;
import java.util.List;

public class PercentageSplit implements SplitStrategy {
    @Override
    public List<Split> calculateSplits(double totalAmount, User paidBy, List<User> participants, List<Double> splitValues) {
        if (participants == null || splitValues == null || participants.size() != splitValues.size()) {
            throw new IllegalArgumentException("Number of participants must match number of percentage values.");
        }

        double sumPercent = 0.0;
        for (double p : splitValues) {
            sumPercent += p;
        }

        if (Math.abs(sumPercent - 100.0) > 0.01) {
            throw new IllegalArgumentException("Sum of percentages (" + sumPercent + "%) must equal 100%.");
        }

        List<Split> splits = new ArrayList<>();
        for (int i = 0; i < participants.size(); i++) {
            double splitAmount = Math.round((totalAmount * (splitValues.get(i) / 100.0)) * 100.0) / 100.0;
            splits.add(new Split(participants.get(i), splitAmount));
        }
        return splits;
    }
}
