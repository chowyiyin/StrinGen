package stringen.logic;

import java.util.Comparator;

public class PairComparator implements Comparator<Pair<Cohort, Cohort>> {

    @Override
    public int compare(Pair<Cohort, Cohort> cohortPair1, Pair<Cohort, Cohort> cohortPair2) {
        if (cohortPair1.getNumberOfSimilarities() > cohortPair2.getNumberOfSimilarities()) {
            return 1;
        } else if (cohortPair1.getNumberOfSimilarities() < cohortPair2.getNumberOfSimilarities()) {
            return -1;
        } else {
            return 0;
        }
    }
}
