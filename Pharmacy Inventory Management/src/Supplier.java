import java.io.Serializable;
import java.util.*;

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

    @Override
    public String toString() {
        return String.format("Supplier{name='%s', location='%s', id='%s', deliveryTimeDays=%d}",
                name, location, id, deliveryTimeDays);
    }

    // Nested static class to manage drugs and their suppliers
    public static class DrugSupplierManager {
        private Map<String, List<Supplier>> drugSupplierMap = new HashMap<>();

        // Add a supplier to a drug
        public void addSupplierToDrug(String drugName, Supplier supplier) {
            drugSupplierMap
                .computeIfAbsent(drugName, k -> new ArrayList<>())
                .add(supplier);
        }

        // Get all suppliers for a specific drug
        public List<Supplier> getSuppliersForDrug(String drugName) {
            return drugSupplierMap.getOrDefault(drugName, new ArrayList<>());
        }

        // Filter suppliers by location
        public List<Supplier> filterSuppliersByLocation(String location) {
            Set<Supplier> result = new HashSet<>();
            for (List<Supplier> suppliers : drugSupplierMap.values()) {
                for (Supplier s : suppliers) {
                    if (s.location.equalsIgnoreCase(location)) {
                        result.add(s);
                    }
                }
            }
            return new ArrayList<>(result);
        }

        // Filter suppliers by delivery time (<= maxDays)
        public List<Supplier> filterSuppliersByDeliveryTime(int maxDays) {
            Set<Supplier> result = new HashSet<>();
            for (List<Supplier> suppliers : drugSupplierMap.values()) {
                for (Supplier s : suppliers) {
                    if (s.deliveryTimeDays <= maxDays) {
                        result.add(s);
                    }
                }
            }
            return new ArrayList<>(result);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        DrugSupplierManager manager = new DrugSupplierManager();

        Supplier s1 = new Supplier("PharmaPlus", "Accra", "S001", 2);
        Supplier s2 = new Supplier("MediLife", "Kumasi", "S002", 5);
        Supplier s3 = new Supplier("HealthDirect", "Accra", "S003", 3);

        manager.addSupplierToDrug("Paracetamol", s1);
        manager.addSupplierToDrug("Paracetamol", s2);
        manager.addSupplierToDrug("Ibuprofen", s3);

        System.out.println("Suppliers for Paracetamol:");
        for (Supplier s : manager.getSuppliersForDrug("Paracetamol")) {
            System.out.println(s);
        }

        System.out.println("\nSuppliers in Accra:");
        for (Supplier s : manager.filterSuppliersByLocation("Accra")) {
            System.out.println(s);
        }

        System.out.println("\nSuppliers with delivery time <= 3 days:");
        for (Supplier s : manager.filterSuppliersByDeliveryTime(3)) {
            System.out.println(s);
        }
    }
}
