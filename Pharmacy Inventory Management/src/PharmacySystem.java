import java.io.*;
import java.util.*;

public class PharmacySystem {
    static HashMap<String, Drug> drugMap = new HashMap<>();
    static Queue<Transaction> purchaseHistory = new LinkedList<>();
    static Stack<Transaction> salesLog = new Stack<>();
    static final String DRUG_FILE = "drugs.dat";
    static final String PURCHASE_FILE = "purchases.dat";
    static final String SALES_FILE = "sales.dat";

    public static void main(String[] args) {
        loadData();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n-- Atinka Meds Pharmacy System --");
            System.out.println("1. Add Drug\n2. List Drugs\n3. Record Purchase\n4. Record Sale\n5. Search Drug\n6. Sort Drugs\n7. View Purchases\n8. View Sales\n9. Save & Exit");
            int choice = safeIntInput(sc, "Choose an option: ");

            switch (choice) {
                case 1 -> addDrug(sc);
                case 2 -> listDrugs();
                case 3 -> recordPurchase(sc);
                case 4 -> recordSale(sc);
                case 5 -> searchDrug(sc);
                case 6 -> sortDrugs(sc);
                case 7 -> viewPurchases();
                case 8 -> viewSales();
                case 9 -> {
                    saveData();
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    static int safeIntInput(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                int value = sc.nextInt();
                sc.nextLine(); // consume newline
                return value;
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // consume invalid input
            }
        }
    }

    static double safeDoubleInput(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextDouble()) {
                double value = sc.nextDouble();
                sc.nextLine();
                return value;
            } else {
                System.out.println("Invalid input. Please enter a decimal number.");
                sc.nextLine();
            }
        }
    }

    static void addDrug(Scanner sc) {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Code: ");
        String code = sc.nextLine();
        System.out.print("Expiration Date: ");
        String exp = sc.nextLine();
        double price = safeDoubleInput(sc, "Price: ");
        int stock = safeIntInput(sc, "Stock: ");
        System.out.print("Suppliers (comma separated): ");
        List<String> suppliers = Arrays.asList(sc.nextLine().split(","));
        drugMap.put(code, new Drug(name, code, exp, price, stock, suppliers));
        System.out.println("Drug added.");
    }

    static void listDrugs() {
        for (Drug d : drugMap.values()) {
            System.out.println(d.code + " | " + d.name + " | Price: " + d.price + " | Stock: " + d.stock);
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
        int qty = safeIntInput(sc, "Quantity: ");
        System.out.print("Buyer ID: ");
        String buyer = sc.nextLine();
        String time = new Date().toString();
        double total = d.price * qty;
        d.stock += qty;
        purchaseHistory.add(new Transaction(code, qty, time, buyer, total));
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
        int qty = safeIntInput(sc, "Quantity: ");
        if (qty > d.stock) {
            System.out.println("Insufficient stock.");
            return;
        }
        System.out.print("Buyer ID: ");
        String buyer = sc.nextLine();
        String time = new Date().toString();
        double total = d.price * qty;
        d.stock -= qty;
        salesLog.push(new Transaction(code, qty, time, buyer, total));
        System.out.println("Sale recorded.");
    }

    static void searchDrug(Scanner sc) {
        System.out.print("Enter drug name/code: ");
        String query = sc.nextLine().toLowerCase();
        for (Drug d : drugMap.values()) {
            if (d.name.toLowerCase().contains(query) || d.code.toLowerCase().contains(query)) {
                System.out.println(d.code + " | " + d.name + " | Price: " + d.price + " | Stock: " + d.stock);
            }
        }
    }

    static void sortDrugs(Scanner sc) {
        List<Drug> list = new ArrayList<>(drugMap.values());
        System.out.println("Sort by: 1) Name 2) Price");
        int opt = safeIntInput(sc, "Choice: ");
        if (opt == 1) {
            DrugSearchAndSort.insertionSortByName(list);
        } else {
            DrugSearchAndSort.insertionSortByPrice(list);
        }
        for (Drug d : list) {
            System.out.println(d.code + " | " + d.name + " | Price: " + d.price);
        }
    }

    static void viewPurchases() {
        for (Transaction t : purchaseHistory) {
            System.out.println(t.timestamp + " | " + t.drugCode + " | Qty: " + t.quantity + " | Buyer: " + t.buyerId);
        }
    }

    static void viewSales() {
        for (Transaction t : salesLog) {
            System.out.println(t.timestamp + " | " + t.drugCode + " | Qty: " + t.quantity + " | Buyer: " + t.buyerId);
        }
    }

    static void saveData() {
        try (ObjectOutputStream o1 = new ObjectOutputStream(new FileOutputStream(DRUG_FILE));
             ObjectOutputStream o2 = new ObjectOutputStream(new FileOutputStream(PURCHASE_FILE));
             ObjectOutputStream o3 = new ObjectOutputStream(new FileOutputStream(SALES_FILE))) {
            o1.writeObject(drugMap);
            o2.writeObject(purchaseHistory);
            o3.writeObject(salesLog);
            System.out.println("Data saved.");
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    static void loadData() {
        try (ObjectInputStream o1 = new ObjectInputStream(new FileInputStream(DRUG_FILE));
             ObjectInputStream o2 = new ObjectInputStream(new FileInputStream(PURCHASE_FILE));
             ObjectInputStream o3 = new ObjectInputStream(new FileInputStream(SALES_FILE))) {
            drugMap = (HashMap<String, Drug>) o1.readObject();
            purchaseHistory = (Queue<Transaction>) o2.readObject();
            salesLog = (Stack<Transaction>) o3.readObject();
            System.out.println("Data loaded.");
        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
        }
    }
}
