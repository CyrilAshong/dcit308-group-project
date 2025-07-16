import java.util.*;

// Utility class for searching and sorting drugs
public class DrugSearchAndSort {

    // Search for a drug by its code
    public static Drug searchByCode(String code, Map<String, Drug> drugMap) {
        return drugMap.get(code);
    }

    // Search for drugs by a partial or full name match
    public static List<Drug> searchByName(String name, Map<String, Drug> drugMap) {
        List<Drug> result = new ArrayList<>();
        for (Drug d : drugMap.values()) {
            if (d.name.toLowerCase().contains(name.toLowerCase())) {
                result.add(d);
            }
        }
        return result;
    }

    // Sort drugs alphabetically by name using insertion sort
    public static void insertionSortByName(List<Drug> list) {
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

    // Sort drugs in ascending order by price
    public static void insertionSortByPrice(List<Drug> list) {
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
}
