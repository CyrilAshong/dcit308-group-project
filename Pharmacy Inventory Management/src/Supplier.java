import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Supplier implements Serializable {
    public String name, location, id;
    public int deliveryTimeDays;

    public Supplier(String name, String location, String id, int deliveryTimeDays) {
        this.name = name;
        this.location = location;
        this.id = id;
        this.deliveryTimeDays = deliveryTimeDays;
    }

    // Filter suppliers by location (case-insensitive partial match)
    public static List<Supplier> filterByLocation(List<Supplier> suppliers, String location) {
        if (location == null || location.trim().isEmpty()) {
            return new ArrayList<>(suppliers);
        }
        String searchLocation = location.toLowerCase();
        return suppliers.stream()
                .filter(s -> s.location != null && s.location.toLowerCase().contains(searchLocation))
                .collect(Collectors.toList());
    }

    // Filter suppliers by maximum delivery time in days
    public static List<Supplier> filterByDeliveryTime(List<Supplier> suppliers, int maxDeliveryDays) {
        if (maxDeliveryDays < 0) {
            return new ArrayList<>();
        }
        return suppliers.stream()
                .filter(s -> s.deliveryTimeDays <= maxDeliveryDays)
                .collect(Collectors.toList());
    }

    // Combined filter for both location and delivery time
    public static List<Supplier> filterSuppliers(List<Supplier> suppliers, String location, int maxDeliveryDays) {
        List<Supplier> result = suppliers;
        if (location != null && !location.trim().isEmpty()) {
            result = filterByLocation(result, location);
        }
        if (maxDeliveryDays >= 0) {
            result = filterByDeliveryTime(result, maxDeliveryDays);
        }
        return result;
    }
}