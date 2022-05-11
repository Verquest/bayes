import java.util.ArrayList;
import java.util.Collection;

public class Vector implements Comparable<Vector>{
    public ArrayList<Double> vals;
    public boolean isActivationVector = false;

    public Vector(double... values) {
        vals = new ArrayList<>();
        for(double d: values)
            vals.add(d);
    }

    public Vector(Collection<Double> values) {
        vals = new ArrayList<>();
        for(double d: values)
            vals.add(d);
    }
    public Vector(Vector v) {
        vals = new ArrayList<>();
        for(double d: v.vals)
            vals.add(d);
    }
    public void setActivation(boolean b) {
        isActivationVector = b;
    }
    public void normalize(){
        ArrayList<Double> valsSquared = new ArrayList<>(vals);

        for(int i = 0; i < valsSquared.size(); i++){
            valsSquared.set(i, valsSquared.get(i)*valsSquared.get(i));
        }

        double sum = valsSquared.stream().mapToDouble(Double::doubleValue).sum();
        double value = valsSquared.stream().mapToDouble(x -> x).sum();
        double squareRooted = Math.sqrt(sum);

        for(int i = 0; i < vals.size(); i++){
            vals.set(i, vals.get(i)/squareRooted);
        }
    }
    //calculate distance between two vectors
    public double distance(Vector v){
        double sum = 0;
        for(int i = 0; i < vals.size(); i++){
            sum += Math.pow(vals.get(i) - v.vals.get(i), 2);
        }
        return Math.sqrt(sum);
    }

    //multiply
    public ArrayList<Double> getVals() {
        return vals;
    }
    public int getSize(){
        return vals.size();
    }

    public double get(int n){
        return vals.get(n);
    }


    public void set(int n, double value){
        vals.set(n, value);
    }
    //add a double to all the values
    public void add(int pos, double value){
        vals.set(pos, value+vals.get(pos));
    }
    //add a value to the vector
    public void add(double value){
        vals.add(value);
    }

    @Override
    public String toString() {
        return vals.toString();
    }

    @Override
    public int compareTo(Vector o) {
        return o.getVals() == vals ? 1 : 0;
    }
}