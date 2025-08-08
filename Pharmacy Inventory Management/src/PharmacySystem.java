import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class PharmacySystem {
    static HashMap<String, Drug> drugMap = new HashMap<>();
    static HashMap<String, Supplier> supplierMap = new HashMap<>();
    static Queue<Transaction> purchaseHistory = new LinkedList<>();
    static Stack<Transaction> salesLog = new Stack<>();

    static final String DRUG_FILE = "drugs.dat";
    static final String SUPPLIER_FILE = "suppliers.dat";
    static final String PURCHASE_FILE = "purchases.dat";
    static final String SALES_FILE = "sales.dat";

    public static void main(String[] args) {
        loadData();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Atinka Meds Pharmacy System ---");
            System.out.println("1. Add Drug\n2. List Drugs\n3. Record Purchase\n4. Record Sale");
            System.out.println("5. Search Drug\n6. Sort Drugs\n7. View Purchases\n8. View Sales");
            System.out.println("9. Add Supplier\n10. List Suppliers\n11. Filter Suppliers\n12. Exit");
            int choice = safeInt(sc, "Choice: ");

            switch (choice) {
                case 1 -> addDrug(sc);
                case 2 -> listDrugs();
                case 3 -> recordPurchase(sc);
                case 4 -> recordSale(sc);
                case 5 -> searchDrug(sc);
                case 6 -> sortDrugs(sc);
                case 7 -> viewPurchases();
                case 8 -> viewSales();
                case 9 -> addSupplier(sc);
                case 10 -> listSuppliers();
                case 11 -> filterSuppliers(sc);
                case 12 -> {
                    saveData();
                    System.out.println("Goodbye.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    static int safeInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                int n = sc.nextInt();
                sc.nextLine();
                return n;
            } else {
                System.out.println("Invalid number.");
                sc.nextLine();
            }
        }
    }

    static double safeDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextDouble()) {
                double d = sc.nextDouble();
                sc.nextLine();
                return d;
            } else {
                System.out.println("Invalid decimal.");
                sc.nextLine();
            }
        }
    }

    static void addDrug(Scanner sc) {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Code: ");
        String code = sc.nextLine();
        if (drugMap.containsKey(code)) {
            System.out.println("Drug code already exists.");
            return;
        }
        System.out.print("Expiration Date: ");
        String exp = sc.nextLine();
        double price = safeDouble(sc, "Price: ");
        int stock = safeInt(sc, "Stock: ");
        System.out.print("Suppliers (comma-separated IDs, or press Enter to skip): ");
        String supplierInput = sc.nextLine();
        List<String> suppliers = supplierInput.isEmpty() ? new ArrayList<>() : Arrays.asList(supplierInput.split(","));

        // Prompt for supplier details if new supplier IDs are provided
        for (String sid : suppliers) {
            String supplierId = sid.trim();
            if (!supplierId.isEmpty() && !supplierMap.containsKey(supplierId)) {
                System.out.println("New supplier detected: " + supplierId);
                System.out.print("Supplier Name: ");
                String supplierName = sc.nextLine();
                System.out.print("Supplier Location: ");
                String location = sc.nextLine();
                int deliveryTime = safeInt(sc, "Delivery Time (days): ");
                supplierMap.put(supplierId, new Supplier(supplierName, location, supplierId, deliveryTime));
            }
        }

        Drug d = new Drug(name, code, exp, price, stock, suppliers);
        drugMap.put(code, d);
        System.out.println("Drug added.");
    }

    static void listDrugs() {
        if (drugMap.isEmpty()) {
            System.out.println("No drugs found.");
            return;
        }
        for (Drug d : drugMap.values()) {
            System.out.printf("%s | %s | ₵%.2f | Stock: %d\n", d.code, d.name, d.price, d.stock);
        }
    }

    static void recordPurchase(Scanner sc) {
        System.out.print("Drug Code: ");
        String code = sc.nextLine();
        Drug d = drugMap.get(code);
        if (d == null) {
            System.out.println("Drug not found.");
            return;
        }
        int qty = safeInt(sc, "Quantity: ");
        System.out.print("Buyer ID: ");
        String buyer = sc.nextLine();
        String time = new Date().toString();
        double total = d.price * qty;
        d.stock += qty;

        Transaction t = new Transaction(code, qty, time, buyer, total);
        purchaseHistory.add(t);
        System.out.println("Purchase recorded.");
    }

    static void recordSale(Scanner sc) {
        System.out.print("Drug Code: ");
        String code = sc.nextLine();
        Drug d = drugMap.get(code);
        if (d == null) {
            System.out.println("Drug not found.");
            return;
        }
        int qty = safeInt(sc, "Quantity: ");
        if (qty > d.stock) {
            System.out.println("Not enough stock.");
            return;
        }
        System.out.print("Buyer ID: ");
        String buyer = sc.nextLine();
        String time = new Date().toString();
        double total = d.price * qty;
        d.stock -= qty;

        Transaction t = new Transaction(code, qty, time, buyer, total);
        salesLog.push(t);
        System.out.println("Sale recorded.");
    }

    static void searchDrug(Scanner sc) {
        System.out.print("Search by name/code: ");
        String q = sc.nextLine().toLowerCase();
        boolean found = false;
        for (Drug d : drugMap.values()) {
            if (d.name.toLowerCase().contains(q) || d.code.toLowerCase().contains(q)) {
                System.out.printf("%s | %s | ₵%.2f | Stock: %d\n", d.code, d.name, d.price, d.stock);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No drugs found.");
        }
    }

    static void sortDrugs(Scanner sc) {
        List<Drug> list = new ArrayList<>(drugMap.values());
        if (list.isEmpty()) {
            System.out.println("No drugs to sort.");
            return;
        }
        System.out.println("Sort by: 1) Name 2) Price");
        int opt = safeInt(sc, "Option: ");
        if (opt == 1) insertionSortByName(list);
        else insertionSortByPrice(list);

        for (Drug d : list) {
            System.out.printf("%s | %s | ₵%.2f\n", d.code, d.name, d.price);
        }
    }

    static void insertionSortByName(List<Drug> list) {
        for (int i = 1; i < list.size(); i++) {
            Drug key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j).name.compareToIgnoreCase(key.name) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    static void insertionSortByPrice(List<Drug> list) {
        for (int i = 1; i < list.size(); i++) {
            Drug key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j).price > key.price) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    static void viewPurchases() {
        if (purchaseHistory.isEmpty()) {
            System.out.println("No purchases recorded.");
            return;
        }
        for (Transaction t : purchaseHistory) {
            System.out.printf("%s | %s | Qty: %d | ₵%.2f | Buyer: %s\n",
                    t.timestamp, t.drugCode, t.quantity, t.totalCost, t.buyerId);
        }
    }

    static void viewSales() {
        if (salesLog.isEmpty()) {
            System.out.println("No sales recorded.");
            return;
        }
        for (Transaction t : salesLog) {
            System.out.printf("%s | %s | Qty: %d | ₵%.2f | Buyer: %s\n",
                    t.timestamp, t.drugCode, t.quantity, t.totalCost, t.buyerId);
        }
    }

    static void addSupplier(Scanner sc) {
        System.out.print("Supplier ID: ");
        String id = sc.nextLine();
        if (supplierMap.containsKey(id)) {
            System.out.println("Supplier ID already exists.");
            return;
        }
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Location: ");
        String location = sc.nextLine();
        int deliveryTime = safeInt(sc, "Delivery Time (days): ");
        supplierMap.put(id, new Supplier(name, location, id, deliveryTime));
        System.out.println("Supplier added.");
    }

    static void listSuppliers() {
        if (supplierMap.isEmpty()) {
            System.out.println("No suppliers found.");
            return;
        }
        for (Supplier s : supplierMap.values()) {
            System.out.printf("%s | %s | %s | Delivery: %d days\n", s.id, s.name, s.location, s.deliveryTimeDays);
        }
    }

    static void filterSuppliers(Scanner sc) {
        System.out.print("Enter location to search (or press Enter to skip): ");
        String location = sc.nextLine();
        int maxDeliveryDays = safeInt(sc, "Enter max delivery time (days, or -1 to skip): ");
        List<Supplier> filtered = Supplier.filterSuppliers(new ArrayList<>(supplierMap.values()), location, maxDeliveryDays);
        if (filtered.isEmpty()) {
            System.out.println("No suppliers match the criteria.");
        } else {
            for (Supplier s : filtered) {
                System.out.printf("%s | %s | %s | Delivery: %d days\n", s.id, s.name, s.location, s.deliveryTimeDays);
            }
        }
    }

    static void saveData() {
        try (ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream(DRUG_FILE));
             ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(PURCHASE_FILE));
             ObjectOutputStream out3 = new ObjectOutputStream(new FileOutputStream(SALES_FILE));
             ObjectOutputStream out4 = new ObjectOutputStream(new FileOutputStream(SUPPLIER_FILE))) {
            out1.writeObject(drugMap);
            out2.writeObject(purchaseHistory);
            out3.writeObject(salesLog);
            out4.writeObject(supplierMap);
            System.out.println("Data saved.");
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    static void loadData() {
        try (ObjectInputStream in1 = new ObjectInputStream(new FileInputStream(DRUG_FILE));
             ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(PURCHASE_FILE));
             ObjectInputStream in3 = new ObjectInputStream(new FileInputStream(SALES_FILE));
             ObjectInputStream in4 = new ObjectInputStream(new FileInputStream(SUPPLIER_FILE))) {
            drugMap = (HashMap<String, Drug>) in1.readObject();
            purchaseHistory = (Queue<Transaction>) in2.readObject();
            salesLog = (Stack<Transaction>) in3.readObject();
            supplierMap = (HashMap<String, Supplier>) in4.readObject();
            System.out.println("Data loaded.");
        } catch (Exception e) {
            System.out.println("Starting with empty system...");
        }
    }
}