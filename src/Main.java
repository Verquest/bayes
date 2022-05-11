import java.sql.BatchUpdateException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Row> rows = new ArrayList<>();
        Loader loader = new Loader();
        rows = loader.load("src/train.txt");
        String activationEttiquette = rows.get(0).getEttiquette();
        ArrayList<TreeSet<String>> uniqueAttrs = getEttiquetteCount(rows);
        ArrayList<Integer> attrCount = getAttributeCount(uniqueAttrs);
        ArrayList<HashMap<String, Integer>> attributeNumericValues = getAttributeNumericValues(uniqueAttrs);
        System.out.println(attrCount);
        Row.setMaxAttributeValues(attrCount);
        ArrayList<Vector> vectors = changeRowToVector(rows, attributeNumericValues, activationEttiquette);
        Bayes bayes = new Bayes(rows.get(0).getAttributeCount(), vectors, attrCount);
        bayes.train();
        rows = loader.load("src/test.txt");
        ArrayList<Vector> testVectors = changeRowToVector(rows, attributeNumericValues, activationEttiquette);
        bayes.test(testVectors);
        //manual input
        System.out.println("Etykieta aktywacji: " + activationEttiquette);
        while(true) {
            ArrayList<String> attributes = manualInput(rows.get(0).getAttributeCount());
            Vector vector = new Vector();
            int i = 0;
            for (String attr : attributes) {
                vector.add(attributeNumericValues.get(i).get(attr));
                i++;
            }
            bayes.test(new ArrayList<Vector>(Arrays.asList(vector)));
        }
    }

    public static ArrayList<String> manualInput(int size){
        int valCount = 0;
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> attributes = new ArrayList<>();

        while(valCount < size){
            System.out.println("Enter the " + valCount + "th attribute: ");
            String attr = scanner.nextLine();
            attributes.add(attr);
            valCount++;
        }
        return attributes;
    }
    public static ArrayList<HashMap<String, Integer>> getAttributeNumericValues(ArrayList<TreeSet<String>> uniqueAttrs) {
        ArrayList<HashMap<String, Integer>> attributeNumericValues = new ArrayList<>();
        for (TreeSet<String> uniqueAttr : uniqueAttrs) {
            HashMap<String, Integer> numericValues = new HashMap<>();
            int i = 0;
            for (String attr : uniqueAttr) {
                numericValues.put(attr, i);
                i++;
            }
            attributeNumericValues.add(numericValues);
        }
        return attributeNumericValues;
    }
    public static ArrayList<Vector> changeRowToVector(ArrayList<Row> rows, ArrayList<HashMap<String, Integer>> attributeNumericValues, String activationEttiquette) {
        ArrayList<Vector> vectors = new ArrayList<>();
        for (Row row : rows) {
            Vector vector = new Vector();
            for (int i = 0; i < row.getAttributeCount(); i++) {
                if (row.getEttiquette().equals(activationEttiquette)) {
                    vector.setActivation(true);
                }
                vector.add(attributeNumericValues.get(i).get(row.getAttribute(i)));
            }
            vectors.add(vector);
        }
        return vectors;
    }
    public static ArrayList<TreeSet<String>> getEttiquetteCount(ArrayList<Row> rows) {
        ArrayList<TreeSet<String>> attributes = new ArrayList<>();
        int attributeCount = rows.get(0).getAttributeCount();

        for (int i = 0; i < attributeCount; i++) {
            attributes.add(new TreeSet<>());
        }

        for (Row row : rows) {
            for(int i = 0; i < attributeCount; i++) {
                attributes.get(i).add(row.getAttribute(i));
            }
        }
        return attributes;
    }
    public static ArrayList<Integer> getAttributeCount(ArrayList<TreeSet<String>> attributes) {
        ArrayList<Integer> attrCount = new ArrayList<>();
        for (TreeSet<String> attribute : attributes) {
            attrCount.add(attribute.size());
        }
        return attrCount;
    }
}
