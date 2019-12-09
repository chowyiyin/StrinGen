package stringen.logic;

import java.util.ArrayList;

public class OrGroupPair extends Pair {

    private ArrayList<AndGroup> similarities = new ArrayList<>();

    public OrGroupPair(OrGroup element1, OrGroup element2) {
        super(element1, element2);
    }

    public void setSimilarities(ArrayList<AndGroup> similarities) {
        this.similarities.addAll(similarities);
        super.setNumberOfSimilarities(similarities.size());
    }

    public ArrayList<AndGroup> getSimilarities() {
        return similarities;
    }

}

