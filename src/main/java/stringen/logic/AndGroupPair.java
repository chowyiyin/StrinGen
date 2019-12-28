package stringen.logic;

public class AndGroupPair extends Pair {

    private AndGroup similarity = new AndGroup();

    public AndGroupPair(AndGroup element1, AndGroup element2) {
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

