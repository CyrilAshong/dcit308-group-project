import java.util.ArrayList;

public class Drug1 {
    private String name;
    private String drugCode;
    private ArrayList<String> suppliers;
    private String expirationDate;
    private double price;
    private int stockLevel;

    public Drug1(String name, String drugCode, ArrayList<String> suppliers,
                 String expirationDate, double price, int stockLevel) {
        this.name = name;
        this.drugCode = drugCode;
        this.suppliers = suppliers;
        this.expirationDate = expirationDate;
        this.price = price;
        this.stockLevel = stockLevel;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public String toFileString() {
        return name + ";" + drugCode + ";" + String.join(",", suppliers) + ";" +
               expirationDate + ";" + price + ";" + stockLevel;
    }

    public static Drug1 fromFileString(String line) {
        String[] parts = line.split(";");
        ArrayList<String> supplierList = new ArrayList<>();
        for (String s : parts[2].split(",")) {
            supplierList.add(s.trim());
        }
        return new Drug1(parts[0], parts[1], supplierList, parts[3],
                         Double.parseDouble(parts[4]), Integer.parseInt(parts[5]));
    }

    public void update(String name, ArrayList<String> suppliers,
                       String expirationDate, double price, int stockLevel) {
        this.name = name;
        this.suppliers = suppliers;
        this.expirationDate = expirationDate;
        this.price = price;
        this.stockLevel = stockLevel;
    }

    public void display() {
        System.out.println("Name: " + name);
        System.out.println("Drug Code: " + drugCode);
        System.out.println("Suppliers: " + String.join(", ", suppliers));
        System.out.println("Expiration Date: " + expirationDate);
        System.out.println("Price: GHS " + price);
        System.out.println("Stock Level: " + stockLevel);
        System.out.println("-------------------------------");
    }
}
