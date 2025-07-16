import java.util.*;

public class SalesLog {
    private Stack<Transaction> sales = new Stack<>();

    public void logSale(Transaction t) {
        sales.push(t);
    }

    public List<Transaction> getSalesInPeriod(Date from, Date to) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : sales) {
            try {
                Date txnDate = new Date(t.timestamp);
                if (!txnDate.before(from) && !txnDate.after(to)) {
                    result.add(t);
                }
            } catch (Exception ignored) {
            }
        }
        return result;
    }

    public Stack<Transaction> getAll() {
        return sales;
    }
}
