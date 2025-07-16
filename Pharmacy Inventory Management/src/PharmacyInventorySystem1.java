import java.io.*;
import java.util.*;

// A basic CLI-based inventory system that stores drug data to a text file
public class PharmacyInventorySystem1 {
    private static final String FILE_NAME = "drugs.txt";
    private static HashMap<String, Drug1> drugMap = new HashMap<>();

    public static void main(String[] args) {
        loadFromFile();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Atinka Meds Inventory System ---");
            System.out.println("1. Add Drug");
            System.out.println("2. Remove Drug");
            System.out.println("3. Update Drug");
            System.out.println("4. List All Drugs");
            System.out.println("5. Exit");
            System.out.print("Select option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    addDrug(scanner);
                    break;
                case 2:
                    removeDrug(scanner);
                    break;
                case 3:
                    updateDrug(scanner);
                    break;
                case 4:
                    listDrugs();
                    break;
                case 5:
                    saveToFile();
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Method to add a new drug
    private static void addDrug(Scanner scanner) {
        System.out.print("Enter Drug Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Drug Code: ");
        String code = scanner.nextLine();

        if (drugMap.containsKey(code)) {
            System.out.println("Drug code already exists.");
            return;
        }

        ArrayList<String> suppliers = new ArrayList<>();
        System.out.print("Enter number of suppliers: ");
        int count = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < count; i++) {
            System.out.print("Supplier " + (i + 1) + ": ");
            suppliers.add(scanner.nextLine());
        }

        System.out.print("Enter Expiration Date (YYYY-MM-DD): ");
        String expDate = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Stock Level: ");
        int stock = scanner.nextInt();
        scanner.nextLine();

        Drug1 drug = new Drug1(name, code, suppliers, expDate, price, stock);
        drugMap.put(code, drug);
        saveToFile();
        System.out.println("Drug added successfully.");
    }

    // Method to remove a drug by its code
    private static void removeDrug(Scanner scanner) {
        System.out.print("Enter Drug Code to remove: ");
        String code = scanner.nextLine();
        if (drugMap.remove(code) != null) {
            saveToFile();
            System.out.println("Drug removed.");
        } else {
            System.out.println("Drug not found.");
        }
    }

    // Method to update an existing drug
    private static void updateDrug(Scanner scanner) {
        System.out.print("Enter Drug Code to update: ");
        String code = scanner.nextLine();
        Drug1 drug = drugMap.get(code);
        if (drug == null) {
            System.out.println("Drug not found.");
            return;
        }

        System.out.print("Enter New Drug Name: ");
        String name = scanner.nextLine();

        ArrayList<String> suppliers = new ArrayList<>();
        System.out.print("Enter number of suppliers: ");
        int count = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < count; i++) {
            System.out.print("Supplier " + (i + 1) + ": ");
            suppliers.add(scanner.nextLine());
        }

        System.out.print("Enter New Expiration Date (YYYY-MM-DD): ");
        String expDate = scanner.nextLine();
        System.out.print("Enter New Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter New Stock Level: ");
        int stock = scanner.nextInt();
        scanner.nextLine();

        drug.update(name, suppliers, expDate, price, stock);
        saveToFile();
        System.out.println("Drug updated.");
    }

    // Method to list all stored drugs
    private static void listDrugs() {
        if (drugMap.isEmpty()) {
            System.out.println("No drugs in inventory.");
            return;
        }

        for (Drug1 drug : drugMap.values()) {
            drug.display();
        }
    }

    // Load drug data from file at startup
    private static void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Drug1 drug = Drug1.fromFileString(line);
                drugMap.put(drug.getDrugCode(), drug);
            }
        } catch (IOException e) {
            System.out.println("No saved data found. Starting fresh.");
        }
    }

    // Save drug data to file for persistence
    private static void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Drug1 drug : drugMap.values()) {
                bw.write(drug.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save data.");
        }
    }
}
