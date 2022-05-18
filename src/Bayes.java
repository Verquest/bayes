import java.util.*;

public class Bayes {
    private ArrayList<Propability> probabilities;
    ArrayList<Vector> vectors;
    private int size;
    private int uniqueEttqsCount = 0;
    private ArrayList<String> uniqueEttiquettes;
    private HashMap<String, Double> uniqueEttiquettesCount;

    public Bayes(int size, ArrayList<Vector> vectors, ArrayList<Integer> uniqueAttrCountPerColumn, ArrayList<TreeSet<String>> uniqueAttrs, HashMap<String, Double> uniqueEttiquettesCount) {
        this.size = size;
        this.vectors = vectors;
        this.uniqueEttiquettesCount = uniqueEttiquettesCount;
        //get unique ettiquettes from vectors
        uniqueEttiquettes = new ArrayList<String>();
        for (int i = 0; i < vectors.size(); i++) {
            if (!uniqueEttiquettes.contains(vectors.get(i).activationString)) {
                uniqueEttiquettes.add(vectors.get(i).activationString);
            }
        }
        uniqueEttqsCount = uniqueEttiquettes.size();
        probabilities = new ArrayList<Propability>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < uniqueAttrCountPerColumn.get(i); j++) {
                for (String ettq : uniqueEttiquettes) {
                    probabilities.add(new Propability(vectors.size(), (double) j, uniqueAttrs.get(i).toArray(new String[0])[j], i, ettq));
                }
            }
        }
        //wygladzanie
        boolean isAnyZero = false;
        for (Propability propability : probabilities) {

                if (propability.getCount() == 0) {
                    isAnyZero = true;
                }

        }
        if (isAnyZero) {
            for (Propability propability : probabilities) {
                propability.increment();
            }

        }
    }

    public void test(ArrayList<Vector> testVectors) {
        int correct = 0;
        int count = testVectors.size();
        for (int t = 0; t < testVectors.size(); t++) {
            Vector vector = testVectors.get(t);
            HashMap<String, Double> props = new HashMap<String, Double>();

            for (String ettq : uniqueEttiquettes) {
                double p = 1;
                p*=uniqueEttiquettesCount.get(ettq);
                for (int i = 0; i < size; i++) {
                    //find the prop to get val from
                    for (Propability prop : probabilities) {
                        if (prop.getAssociatedCol() == i && prop.getValue() == (int) vector.get(i) && prop.getActivationEttiquette().equals(ettq)) {
                            p *= prop.getProbability();
                            break;
                        }
                    }
                }
                props.put(ettq, p);
            }

            //get max ettiquette from propabilites
            String maxEttiquette = "";
            double maxProbability = 0;
            for (String etq : props.keySet()) {
                System.out.println("Propability for: " + etq + " is: " + props.get(etq));
                if (props.get(etq) > maxProbability) {
                    maxProbability = props.get(etq);
                    maxEttiquette = etq;
                }
            }
            if (maxEttiquette.equals(vector.activationString)) {
                correct++;
            }
            if (count == 1) {
                System.out.println("Uzyskana: " + maxEttiquette);
            }
        }
        if (count != 1) {
            System.out.println("Correct: " + correct + " out of " + count);
            for (Propability propability : probabilities) {
                System.out.println("Column: " + propability.getAssociatedCol() +
                        ", ettiquette: " + propability.getEttiquette() +
                        " | " + propability.getActivationEttiquette() +
                        ", propability: " + propability.getProbability() +
                        ", count: " + propability.getCount() +
                        ", total: " + propability.getTotal());
            }
        }
    }

    public void train() {
        for (int i = 0; i < vectors.size(); i++) {
            String ettq = vectors.get(i).activationString;
            for (int j = 0; j < size; j++) {
                incrementProbability(j,  vectors.get(i).ettiquettes.get(j), ettq);
            }
        }
    }

    private void incrementProbability(int index, String ettiquette, String activationEttiquette) {
        Propability p = null;
        for (int i = 0; i < probabilities.size(); i++) {
            p = probabilities.get(i);
            if (p.getAssociatedCol() == index && p.getEttiquette().equals(ettiquette) && p.getActivationEttiquette().equals(activationEttiquette)) {
                p.increment();
            }
        }
    }
}