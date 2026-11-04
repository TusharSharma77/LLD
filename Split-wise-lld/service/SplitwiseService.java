import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SplitwiseService {
    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private final Map<String, Group> groups = new ConcurrentHashMap<>();
    private final List<Expense> expenses = new ArrayList<>();
    private final List<Transaction> settlementHistory = new ArrayList<>();

    public User createUser(int id, String name, String email, String phone) {
        User user = new User(id, name, email, phone);
        users.put(id, user);
        return user;
    }

    public Group createGroup(String id, String name) {
        Group group = new Group(id, name);
        groups.put(id, group);
        return group;
    }

    public void addUserToGroup(String groupId, User user) {
        Group group = groups.get(groupId);
        if (group != null) {
            group.addUser(user);
        }
    }

    public Expense addExpense(String description, double amount, User paidBy, List<User> participants,
                              SplitStrategy splitStrategy, List<Double> splitValues, Group group) {
        // 1. Build the expense and calculate splits via strategy
        Expense expense = new Expense.ExpenseBuilder()
                .setId(UUID.randomUUID().toString())
                .setDescription(description)
                .setAmount(amount)
                .setPaidBy(paidBy)
                .setParticipants(participants)
                .setSplitStrategy(splitStrategy)
                .setSplitValues(splitValues)
                .build();

        // 2. Adjust balance sheets between payer and participants
        for (Split split : expense.getSplits()) {
            User participant = split.getUser();
            double splitAmount = split.getAmount();

            if (!participant.equals(paidBy)) {
                // paidBy is owed money by participant (+ve for paidBy)
                paidBy.getBalanceSheet().adjustBalance(participant, splitAmount);
                // participant owes money to paidBy (-ve for participant)
                participant.getBalanceSheet().adjustBalance(paidBy, -splitAmount);
            }
        }

        // 3. Record expense history
        expenses.add(expense);
        if (group != null) {
            group.addExpense(expense);
        }

        return expense;
    }

    public Transaction settlePayment(User from, User to, double amount) {
        // Adjust balance sheets to reflect payment
        from.getBalanceSheet().adjustBalance(to, amount);
        to.getBalanceSheet().adjustBalance(from, -amount);

        Transaction transaction = new Transaction(from, to, amount);
        settlementHistory.add(transaction);
        return transaction;
    }

    public List<Transaction> simplifyGroupDebts(Group group) {
        // Calculate net balance for each member in this group
        Map<User, Double> netBalances = new HashMap<>();
        for (User user : group.getUsers()) {
            double net = 0.0;
            for (Map.Entry<User, Double> entry : user.getBalanceSheet().getBalances().entrySet()) {
                if (group.getUsers().contains(entry.getKey())) {
                    net += entry.getValue();
                }
            }
            if (Math.abs(net) > 0.01) {
                netBalances.put(user, net);
            }
        }

        // Separate creditors and debtors
        PriorityQueue<Map.Entry<User, Double>> debtors = new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue));
        PriorityQueue<Map.Entry<User, Double>> creditors = new PriorityQueue<>((a, b) -> Double.compare(b.getValue(), a.getValue()));

        for (Map.Entry<User, Double> entry : netBalances.entrySet()) {
            if (entry.getValue() < -0.01) {
                debtors.add(entry);
            } else if (entry.getValue() > 0.01) {
                creditors.add(entry);
            }
        }

        List<Transaction> settlementTransactions = new ArrayList<>();

        while (!debtors.isEmpty() && !creditors.isEmpty()) {
            Map.Entry<User, Double> debtorEntry = debtors.poll();
            Map.Entry<User, Double> creditorEntry = creditors.poll();

            double debt = -debtorEntry.getValue();
            double credit = creditorEntry.getValue();

            double settledAmount = Math.min(debt, credit);
            settlementTransactions.add(new Transaction(debtorEntry.getKey(), creditorEntry.getKey(), settledAmount));

            double remainingDebt = debt - settledAmount;
            double remainingCredit = credit - settledAmount;

            if (remainingDebt > 0.01) {
                debtors.add(new AbstractMap.SimpleEntry<>(debtorEntry.getKey(), -remainingDebt));
            }
            if (remainingCredit > 0.01) {
                creditors.add(new AbstractMap.SimpleEntry<>(creditorEntry.getKey(), remainingCredit));
            }
        }

        return settlementTransactions;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public List<Transaction> getSettlementHistory() {
        return settlementHistory;
    }
}
