import java.util.ArrayList;
import java.util.List;

public class Group {
    private String id;
    private String name;
    private List<User> users;
    private List<Expense> expenses;

    public Group(String id, String name) {
        this.id = id;
        this.name = name;
        this.users = new ArrayList<>();
        this.expenses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
        }
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }
}
