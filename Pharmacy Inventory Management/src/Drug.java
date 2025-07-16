import java.io.Serializable;
import java.util.List;

// Represents a drug in the pharmacy system with attributes for tracking inventory
public class Drug implements Serializable {
    public String name, code, expirationDate;
    public double price;
    public int stock;
    public List<String> suppliers;

    public Drug(String name, String code, String expirationDate, double price, int stock, List<String> suppliers) {
        this.name = name;
        this.code = code;
        this.expirationDate = expirationDate;
        this.price = price;
        this.stock = stock;
        this.suppliers = suppliers;
    }
}
