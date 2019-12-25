package stringen.logic;

import java.util.Comparator;

public class PairComparator implements Comparator<Pair> {

    @Override
    public int compare(Pair cohortPair1, Pair cohortPair2) {
        if (cohortPair1.getNumberOfSimilarities() > cohortPair2.getNumberOfSimilarities()) {
            return 1;
        } else if (cohortPair1.getNumberOfSimilarities() < cohortPair2.getNumberOfSimilarities()) {
            return -1;
        } else {
            return 0;
        }
    }
}
