package stringen.logic;

import java.util.ArrayList;

import stringen.logic.prerequisites.Prerequisite;

public class Pair<K,V> {
    private K element1;
    private V element2;
    private int numberOfSimilarities = 0;
    private ArrayList<Group> similarities = new ArrayList<>();

    public Pair(K element1, V element2) {
        this.element1 = element1;
        this.element2 = element2;
    }

    public K getFirstElement() {
        return element1;
    }

    public V getSecondElement() {
        return element2;
    }

    public int getNumberOfSimilarities() {
        return numberOfSimilarities;
    }

    public void setNumberOfSimilarities(int numberOfSimilarities) {
        this.numberOfSimilarities = numberOfSimilarities;
    }

    public void setSimilarities(ArrayList<Group> similarities) {
        this.similarities.addAll(similarities);
    }

    public ArrayList<Group> getSimilarities() {
        return similarities;
    }

    public String generateString() {
        return StringGenerator.generateString(similarities);
    }

    public boolean contains(Pair<Cohort, Cohort> otherPair) {
        Cohort otherPairFirstCohort = otherPair.getFirstElement();
        Cohort otherPairSecondCohort = otherPair.getSecondElement();
        return otherPairFirstCohort.equals(element1)
                || otherPairFirstCohort.equals(element2)
                || otherPairSecondCohort.equals(element1)
                || otherPairSecondCohort.equals(element2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof Pair) {
            return (element1.equals(((Pair) o).getFirstElement())
                    && element2.equals(((Pair) o).getSecondElement()))
                    || (element1.equals(((Pair) o).getSecondElement())
                    && element2.equals(((Pair) o).getFirstElement()));
        } else {
            return false;
        }
    }
}
