package stringen.logic;

import java.util.ArrayList;

public class CohortPair extends Pair {

    private ArrayList<OrGroup> similarities = new ArrayList<>();

    public CohortPair(Cohort element1, Cohort element2) {
        super(element1, element2);
    }

    public void setSimilarities(ArrayList<OrGroup> similarities) {
        this.similarities.addAll(similarities);
        super.updateNumberOfSimilarities(similarities.size());
    }

    public ArrayList<OrGroup> getSimilarities() {
        return similarities;
    }

}

