import java.util.*;

public class PurchaseHistory {
    private Queue<Transaction> purchases = new LinkedList<>();

    public void logPurchase(Transaction t) {
        purchases.add(t);
    }

    public List<Transaction> getRecentPurchases(String drugCode, int limit) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : purchases) {
            if (t.drugCode.equals(drugCode)) {
                result.add(t);
            }
        }
        result.sort(Comparator.comparing(t -> t.timestamp));
        Collections.reverse(result);
        return result.subList(0, Math.min(limit, result.size()));
    }

    public Queue<Transaction> getAll() {
        return purchases;
    }
}
