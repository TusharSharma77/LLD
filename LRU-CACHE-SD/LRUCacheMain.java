public class LRUCacheMain {
    public static void main(String[] args) {
        System.out.println("--- LRU Cache Demo (Capacity = 3) ---");
        LRUCache<String, Integer> cache = new LRUCache<>(3);

        // 1. Put initial elements
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);
        System.out.println("Added A=1, B=2, C=3 (Current Size: " + cache.size() + ")");

        // 2. Access 'A' (A becomes Most Recently Used)
        System.out.println("Get A: " + cache.get("A")); // prints 1

        // 3. Add 'D' (Capacity full, B is Least Recently Used and should be evicted)
        cache.put("D", 4);
        System.out.println("Added D=4 (B should be evicted)");

        // 4. Verify B was evicted
        System.out.println("Get B: " + cache.get("B")); // prints null (evicted)
        System.out.println("Get C: " + cache.get("C")); // prints 3
        System.out.println("Get D: " + cache.get("D")); // prints 4
        System.out.println("Get A: " + cache.get("A")); // prints 1

        // 5. Update existing key
        cache.put("C", 30);
        System.out.println("Updated C to 30: " + cache.get("C"));
    }
}
