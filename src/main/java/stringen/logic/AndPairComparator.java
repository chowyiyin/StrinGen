package stringen.logic;

import java.util.Comparator;

public class AndPairComparator implements Comparator<AndGroupPair> {

    @Override
    public int compare(AndGroupPair pair1, AndGroupPair pair2) {
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
