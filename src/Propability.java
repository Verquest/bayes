public class Propability implements Comparable<Propability> {
    private int total;
    private int count;
    private double value;
    private boolean isActivated;
    public Propability(int total, double value, boolean isActivated) {
        this.total = total;
        this.value = value;
        this.isActivated = isActivated;
    }
    public void increment() {
        this.count++;
        this.total++;
    }
    public void incrementTotal(){
        this.total++;
    }
    public double getProbability() {
        return (double)(count/total);
    }
    public double getValue() {
        return this.count;
    }
    public boolean isActivated() {
        return this.isActivated;
    }
    @Override
    public int compareTo(Propability o) {
        if (this.value == o.value && this.isActivated() == o.isActivated()) {
            return 0;
        }
        return -1;
    }
}
