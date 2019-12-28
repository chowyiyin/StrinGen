package stringen.logic;

import java.util.Comparator;

public class OrPairComparator implements Comparator<OrGroupPair> {

    @Override
    public int compare(OrGroupPair pair1, OrGroupPair pair2) {
        if (pair1.getSimilarity().size() > pair2.getSimilarity().size()) {
            return -1;
        } else if (pair1.getSimilarity().size() < pair2.getSimilarity().size()) {
            return 1;
        } else {
            if (pair1.estimateLength() > pair2.estimateLength()) {
                return -1;
            } else if (pair1.estimateLength() < pair2.estimateLength()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
