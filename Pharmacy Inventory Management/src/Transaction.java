import java.io.Serializable;

public class Transaction implements Serializable {
    public String drugCode, buyerId, timestamp;
    public int quantity;
    public double totalCost;

    public Transaction(String drugCode, int quantity, String timestamp, String buyerId, double totalCost) {
        this.drugCode = drugCode;
        this.quantity = quantity;
        this.timestamp = timestamp;
        this.buyerId = buyerId;
        this.totalCost = totalCost;
    }
}
