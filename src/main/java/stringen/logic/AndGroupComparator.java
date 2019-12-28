package stringen.logic;

import java.util.Comparator;

public class AndGroupComparator implements Comparator<AndGroup> {


    @Override
    public int compare(AndGroup andGroup1, AndGroup andGroup2) {
        if (andGroup1.size() > andGroup2.size()) {
            return -1;
        } else if (andGroup1.size() < andGroup2.size()) {
            return 1;
        } else {
            return 0;
        }
    }
}
