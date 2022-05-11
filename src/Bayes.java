import java.util.ArrayList;

public class Bayes {
    private ArrayList<ArrayList<Propability>> probabilities;
    ArrayList<Vector> vectors;
    private int size;

    public Bayes(int size, ArrayList<Vector> vectors, ArrayList<Integer> sizes) {
        this.size = size;
        this.vectors = vectors;
        probabilities = new ArrayList<ArrayList<Propability>>();
        for (int i = 0; i < size; i++) {
            probabilities.add(new ArrayList<Propability>());
            for (int j = 0; j < sizes.get(i); j++) {
                probabilities.get(i).add(new Propability(vectors.size(), (double)j, true));
                probabilities.get(i).add(new Propability(vectors.size(), (double)j, false));
            }
        }
    }

    public void test(ArrayList<Vector> testVectors) {
        int correct = 0;
        int count = testVectors.size();
        for (int t = 0; t < testVectors.size(); t++) {
            Vector vector = testVectors.get(t);
            ArrayList<Double> activationProbabilities = new ArrayList<Double>();
            ArrayList<Double> nonActivationProbabilities = new ArrayList<Double>();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < probabilities.get(i).size(); j++) {
                    Propability p = probabilities.get(i).get(j);
                    if (p.getValue() == vector.get(i)) {
                        if (p.isActivated()) {
                            activationProbabilities.add(p.getProbability());
                        } else {
                            nonActivationProbabilities.add(p.getProbability());
                        }
                    }
                }
            }
            double activationProbability = 0;
            double nonActivationProbability = 0;
            activationProbability = activationProbabilities.stream().reduce(1.0, (a, b) -> a * b);
            nonActivationProbability = nonActivationProbabilities.stream().reduce(1.0, (a, b) -> a * b);
            if (activationProbability > nonActivationProbability) {
                if (vector.isActivationVector) {
                    correct++;
                }
            }else if(!vector.isActivationVector){
                correct++;
            }
            if(count == 1){
                System.out.println("Uzyskana: " + (activationProbability>nonActivationProbability));
            }
        }
        if(count!=1)
            System.out.println("Correct: " + correct + " out of " + count);
    }

    public void train() {
        for (int i = 0; i < vectors.size(); i++) {
            boolean isActivated = vectors.get(i).isActivationVector;
            for (int j = 0; j < size; j++) {
                incrementProbability(j, (int)vectors.get(i).get(j), isActivated);
            }
        }
    }
    private void incrementProbability(int index, int value, boolean isActivated) {
        for (int i = 0; i < probabilities.get(index).size(); i++) {
            Propability p = probabilities.get(index).get(i);
            if (p.getValue() == value && p.isActivated() == isActivated) {
                p.increment();
            }
            if (p.getValue() == value && p.isActivated() != isActivated){
                p.incrementTotal();
            }
        }
    }
}