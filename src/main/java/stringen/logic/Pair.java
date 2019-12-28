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

    public void updateNumberOfSimilarities(int numberOfSimilarities) {
        this.numberOfSimilarities = numberOfSimilarities;
    }

    public int getNumberOfSimilarities() {
        return numberOfSimilarities;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof Pair) {
            Pair other = (Pair) o;
            return (getFirstElement().equals(other.getFirstElement()) &&
                        getSecondElement().equals(other.getSecondElement())) ||
                    (getFirstElement().equals(other.getSecondElement()) &&
                            getSecondElement().equals(other.getFirstElement()));
        } else {
            return false;
        }
    }
}
