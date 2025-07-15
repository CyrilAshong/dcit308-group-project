import java.io.Serializable;

public class Supplier implements Serializable {
    public String name;
    public String location;
    public String id;
    public int deliveryTimeDays;

    public Supplier(String name, String location, String id, int deliveryTimeDays) {
        this.name = name;
        this.location = location;
        this.id = id;
        this.deliveryTimeDays = deliveryTimeDays;
    }
}
