import java.sql.BatchUpdateException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Row> rows = new ArrayList<>();
        Loader loader = new Loader();
        rows = loader.load("src/train.txt");
        ArrayList<TreeSet<String>> uniqueAttrs = getEttiquetteCount(rows);
        ArrayList<Integer> attrCount = getAttributeCount(uniqueAttrs);
        ArrayList<HashMap<String, Integer>> attributeNumericValues = getAttributeNumericValues(uniqueAttrs);
        System.out.println(attrCount);
        Row.setMaxAttributeValues(attrCount);
        ArrayList<Vector> vectors = changeRowToVector(rows, attributeNumericValues);
        ArrayList<String> uniqueEttiquettes = new ArrayList<String>();
        for (int i = 0; i < vectors.size(); i++) {
            if (!uniqueEttiquettes.contains(vectors.get(i).activationString)) {
                uniqueEttiquettes.add(vectors.get(i).activationString);
            }
        }
        HashMap<String, Double> activationEttiquettesCount = ettiquetteCount(rows, uniqueEttiquettes);
        Bayes bayes = new Bayes(rows.get(0).getAttributeCount(), vectors, attrCount, uniqueAttrs, activationEttiquettesCount);
        bayes.train();
        rows = loader.load("src/test.txt");
        ArrayList<Vector> testVectors = changeRowToVector(rows, attributeNumericValues);
        bayes.test(testVectors);
        //manual input
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

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the line attribute: ");
        String attr = scanner.nextLine();
        System.out.println(attr);
        return new ArrayList<>(Arrays.asList(attr.split(",")));
    }

    public static HashMap<String, Double> ettiquetteCount(ArrayList<Row> rows, ArrayList<String> uniqueEttiquettes) {
        HashMap<String, Double> ettiquetteCount = new HashMap<>();
        for(String ettiquette : uniqueEttiquettes) {
            ettiquetteCount.put(ettiquette, 0.0);
        }
        for (Row row : rows) {
            ettiquetteCount.put(row.getEttiquette(), ettiquetteCount.get(row.getEttiquette()) + 1);
        }
        return ettiquetteCount;
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
    public static ArrayList<Vector> changeRowToVector(ArrayList<Row> rows, ArrayList<HashMap<String, Integer>> attributeNumericValues) {
        ArrayList<Vector> vectors = new ArrayList<>();
        for (Row row : rows) {
            Vector vector = new Vector();
            vector.activationString = row.getEttiquette();
            for (int i = 0; i < row.getAttributeCount(); i++) {
                vector.ettiquettes.add(row.attributes.get(i));
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
