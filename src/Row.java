import java.util.ArrayList;

public class Row {
    String ettiquette;
    ArrayList<String> attributes;
    static ArrayList<Integer> maxAttributeValue = new ArrayList<Integer>();
    ArrayList<Integer> numericAttributes;
    public Row(String ettiquette, ArrayList<String> attributes) {
        this.ettiquette = ettiquette;
        this.attributes = attributes;
    }
    public String getEttiquette() {
        return ettiquette;
    }
    public ArrayList<String> getAttributes() {
        return attributes;
    }
    public String getAttribute(int index) {
        return attributes.get(index);
    }
    public int getAttributeCount() {
        return attributes.size();
    }
    public ArrayList<Integer> getNumericAttributes() {
        return numericAttributes;
    }
    public void setNumericAttributes(ArrayList<Integer> numericAttributes) {
        this.numericAttributes = numericAttributes;
    }
    public static void setNthMaxAttributeValue(int n, int value) {
        maxAttributeValue.set(n, value);
    }
    public static void setMaxAttributeValues(ArrayList<Integer> maxAttributeValues) {
        maxAttributeValue = maxAttributeValues;
    }
}
