import java.util.*;

public class DrugSearchAndSort {

    public static Drug searchByCode(String code, Map<String, Drug> drugMap) {
        return drugMap.get(code);
    }

    public static List<Drug> searchByName(String name, Map<String, Drug> drugMap) {
        List<Drug> result = new ArrayList<>();
        for (Drug d : drugMap.values()) {
            if (d.name.toLowerCase().contains(name.toLowerCase())) {
                result.add(d);
            }
        }
        return result;
    }

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
