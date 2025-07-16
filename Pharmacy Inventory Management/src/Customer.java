import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Represents a pharmacy customer with a list of their transactions
public class Customer implements Serializable {
    public String id, name, contact;
    public List<Transaction> transactions;

    public Customer(String id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.transactions = new ArrayList<>(); // Initialize empty transaction list
    }

    // Add a transaction record to this customer
    public void addTransaction(Transaction t) {
        transactions.add(t);
    }
}
