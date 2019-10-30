package stringen.logic.prerequisites;

import stringen.logic.Cohort;

public interface Prerequisite {

    void addCohort(Cohort cohort);

    String generateString();

    default String appendBrackets(String value) {
        return "(" + value + ")";
    }

}
