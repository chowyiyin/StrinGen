package stringen.logic;

import java.util.Comparator;

public class CohortPairComparator implements Comparator<Pair<Cohort, Cohort>> {

    @Override
    public int compare(Pair<Cohort, Cohort> firstPair, Pair<Cohort, Cohort> secondPair) {
        int firstPairSimilarities = firstPair.getNumberOfSimilarities();
        int secondPairSimilarities = secondPair.getNumberOfSimilarities();
        if (firstPairSimilarities > secondPairSimilarities) {
            return 1;
        } else if (firstPairSimilarities < secondPairSimilarities) {
            return - 1;
        } else {
            return 0;
        }
    }
}
