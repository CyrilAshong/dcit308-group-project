import java.util.*;

public class StockMonitor {
    private PriorityQueue<Drug> lowStockQueue;

    public StockMonitor() {
        lowStockQueue = new PriorityQueue<>(Comparator.comparingInt(d -> d.stock));
    }

    public void monitor(Collection<Drug> drugs, int threshold) {
        lowStockQueue.clear();
        for (Drug d : drugs) {
            if (d.stock < threshold) {
                lowStockQueue.offer(d);
            }
        }
    }

    public void displayLowStock() {
        if (lowStockQueue.isEmpty()) {
            System.out.println("All drugs are sufficiently stocked.");
        } else {
            System.out.println("Low Stock Drugs:");
            for (Drug d : lowStockQueue) {
                System.out.println(d.name + " | Code: " + d.code + " | Stock: " + d.stock);
            }
        }
    }
}
