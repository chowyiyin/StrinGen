package stringen.logic.prerequisites;

import stringen.logic.Cohort;

public interface Prerequisite {

    String generateString();

    default String appendBrackets(String value) {
        return "(" + value + ")";
    }

}
