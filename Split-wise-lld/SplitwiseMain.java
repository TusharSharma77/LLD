import java.util.Arrays;
import java.util.List;

public class SplitwiseMain {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("            SPLITWISE LOW LEVEL DESIGN            ");
        System.out.println("==================================================");

        SplitwiseService splitwiseService = new SplitwiseService();

        // 1. Create Users
        User alice = splitwiseService.createUser(1, "Alice", "alice@example.com", "9876543210");
        User bob = splitwiseService.createUser(2, "Bob", "bob@example.com", "9876543211");
        User charlie = splitwiseService.createUser(3, "Charlie", "charlie@example.com", "9876543212");

        List<User> groupMembers = Arrays.asList(alice, bob, charlie);

        // 2. Create Group
        Group goaTrip = splitwiseService.createGroup("G1", "Goa Vacation");
        splitwiseService.addUserToGroup("G1", alice);
        splitwiseService.addUserToGroup("G1", bob);
        splitwiseService.addUserToGroup("G1", charlie);

        System.out.println("\n--- Step 1: Created Group '" + goaTrip.getName() + "' with members: Alice, Bob, Charlie ---");

        // 3. Expense 1: EQUAL SPLIT ($300 paid by Alice for Resort Villa)
        System.out.println("\n--- Step 2: Adding Expense 1 (Equal Split) ---");
        System.out.println("Alice pays $300 for 'Resort Villa' shared equally among Alice, Bob, Charlie.");
        splitwiseService.addExpense(
                "Resort Villa",
                300.0,
                alice,
                groupMembers,
                new EqualSplit(),
                null,
                goaTrip
        );

        // 4. Expense 2: EXACT SPLIT ($150 paid by Bob for Seafood Dinner)
        System.out.println("\n--- Step 3: Adding Expense 2 (Exact Split) ---");
        System.out.println("Bob pays $150 for 'Seafood Dinner': Alice=$50, Bob=$40, Charlie=$60.");
        splitwiseService.addExpense(
                "Seafood Dinner",
                150.0,
                bob,
                groupMembers,
                new ExactSplit(),
                Arrays.asList(50.0, 40.0, 60.0), // Alice: 50, Bob: 40, Charlie: 60
                goaTrip
        );

        // 5. Expense 3: PERCENTAGE SPLIT ($200 paid by Charlie for Scuba Diving)
        System.out.println("\n--- Step 4: Adding Expense 3 (Percentage Split) ---");
        System.out.println("Charlie pays $200 for 'Scuba Diving': Alice=40% ($80), Bob=30% ($60), Charlie=30% ($60).");
        splitwiseService.addExpense(
                "Scuba Diving",
                200.0,
                charlie,
                groupMembers,
                new PercentageSplit(),
                Arrays.asList(40.0, 30.0, 30.0), // Alice: 40%, Bob: 30%, Charlie: 30%
                goaTrip
        );

        // 6. Display Current Balance Sheets for each user
        System.out.println("\n==================================================");
        System.out.println("             CURRENT BALANCE SHEETS               ");
        System.out.println("==================================================");
        alice.getBalanceSheet().showBalances();
        System.out.println();
        bob.getBalanceSheet().showBalances();
        System.out.println();
        charlie.getBalanceSheet().showBalances();

        // 7. Calculate Simplified Debt Settlements
        System.out.println("\n==================================================");
        System.out.println("            SIMPLIFIED DEBT SETTLEMENTS           ");
        System.out.println("==================================================");
        List<Transaction> transactions = splitwiseService.simplifyGroupDebts(goaTrip);
        if (transactions.isEmpty()) {
            System.out.println("No settlements needed! All balanced.");
        } else {
            System.out.println("Minimum transactions to settle the entire group:");
            for (Transaction tx : transactions) {
                System.out.println("  -> " + tx);
            }
        }

        // 8. Settle Up (Bob pays Alice $50)
        System.out.println("\n--- Step 5: Bob pays Alice $50 to settle partial dues ---");
        Transaction settlement = splitwiseService.settlePayment(bob, alice, 50.0);
        System.out.println("Payment recorded: " + settlement);

        // 9. Show updated balance sheets
        System.out.println("\n==================================================");
        System.out.println("        BALANCE SHEETS AFTER BOB'S PAYMENT        ");
        System.out.println("==================================================");
        alice.getBalanceSheet().showBalances();
        System.out.println();
        bob.getBalanceSheet().showBalances();
    }
}
