public class Propability implements Comparable<Propability> {
    private int total;
    private int count;
    private double value;
    private String ettiquette;
    private String activationEttiquette;
    private int associatedCol;
    public Propability(int total, double value, String ettiquette, int associatedCol, String activationEttiquette) {
        this.total = total;
        this.value = value;
        this.ettiquette = ettiquette;
        this.associatedCol = associatedCol;
        this.activationEttiquette = activationEttiquette;
    }
    public void increment() {
        this.count++;
    }
    public void incrementTotal(){
        this.total++;
    }
    public double getProbability() {
        return count/(double)total;
    }
    public int getTotal() {
        return total;
    }

    public int getAssociatedCol() {
        return associatedCol;
    }

    public double getValue() {
        return this.value;
    }
    public int getCount() {
        return this.count;
    }
    public String getEttiquette() {
        return this.ettiquette;
    }
    public String getActivationEttiquette() {
        return this.activationEttiquette;
    }

    @Override
    public int compareTo(Propability o) {
        if (this.value == o.value && this.getActivationEttiquette().equals(o.getActivationEttiquette())) {
            return 0;
        }
        return -1;
    }
}
