package stringen.logic;

public class OrGroupPair extends Pair {

    private AndGroup similarity = new AndGroup();

    public OrGroupPair(OrGroup element1, OrGroup element2) {
        super(element1, element2);
    }

    public void setSimilarity(AndGroup similarity) {
        this.similarity = similarity;
    }

    public AndGroup getSimilarity() {
        return similarity;
    }

    public int estimateLength() {
        return similarity.generateString().length();
    }
}

