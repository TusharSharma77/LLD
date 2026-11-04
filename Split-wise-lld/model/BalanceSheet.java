import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BalanceSheet {
    private final User owner;
    private final Map<User, Double> balances = new ConcurrentHashMap<>();

    public BalanceSheet(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public Map<User, Double> getBalances() {
        return balances;
    }

    public synchronized void adjustBalance(User otherUser, double amount) {
        if (owner.equals(otherUser)) {
            return; // Cannot owe yourself
        }
        balances.merge(otherUser, amount, Double::sum);
        // If balance becomes negligible, clean up
        if (Math.abs(balances.getOrDefault(otherUser, 0.0)) < 0.001) {
            balances.remove(otherUser);
        }
    }

    public double getBalance(User otherUser) {
        return balances.getOrDefault(otherUser, 0.0);
    }

    public void showBalances() {
        System.out.println("--- Balance Sheet for " + owner.getName() + " ---");
        if (balances.isEmpty()) {
            System.out.println("All settled up! No dues.");
            return;
        }

        double totalOwedToMe = 0;
        double totalIOwe = 0;

        for (Map.Entry<User, Double> entry : balances.entrySet()) {
            User otherUser = entry.getKey();
            double amount = entry.getValue();

            if (amount > 0.01) {
                System.out.println("  * " + otherUser.getName() + " owes " + owner.getName() + ": $" + String.format("%.2f", amount));
                totalOwedToMe += amount;
            } else if (amount < -0.01) {
                System.out.println("  * " + owner.getName() + " owes " + otherUser.getName() + ": $" + String.format("%.2f", -amount));
                totalIOwe += (-amount);
            }
        }
        System.out.println("Summary -> Total Owed to " + owner.getName() + ": $" + String.format("%.2f", totalOwedToMe)
                + " | Total " + owner.getName() + " Owes: $" + String.format("%.2f", totalIOwe));
    }
}
