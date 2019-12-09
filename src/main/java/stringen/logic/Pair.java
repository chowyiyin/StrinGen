package stringen.logic;

public class Pair<K, V> {

    private K element1;
    private V element2;

    private int numberOfSimilarities = 0;

    public Pair(K element1, V element2) {
        this.element1 = element1;
        this.element2= element2;
    }

    public K getFirstElement() {
        return element1;
    }

    public V getSecondElement() {
        return element2;
    }

    public void setNumberOfSimilarities(int numberOfSimilarities) {
        this.numberOfSimilarities = numberOfSimilarities;
    }

    public int getNumberOfSimilarities() {
        return numberOfSimilarities;
    }
}
