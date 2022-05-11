import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Loader {
    public Loader(){}
    public ArrayList<Row> load(String path){
        ArrayList<Row> rows = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                LinkedList<String> lineValues = new LinkedList<>(Arrays.asList(line.split(",")));
                String className = lineValues.removeLast();
                Row row = new Row(className, new ArrayList<String>(lineValues));
                rows.add(row);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error reading training data.");
        }
        return rows;
    }
}