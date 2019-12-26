package stringen.logic.requirements;

import stringen.logic.StringGenerator;

public class YearPreclusion implements Requirement {

    public static String PREFIX = "YEAR_AR";

    private String startYear;
    private String endYear;

    public YearPreclusion(String startYear, String endYear) {
        this.startYear = startYear;
        this.endYear = endYear;
    }

    @Override
    public String generateString() {
        return PREFIX + StringGenerator.appendBrackets(startYear + "," + endYear);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof YearPreclusion) {
                YearPreclusion other = (YearPreclusion) o;
                return startYear.equals(other.startYear) &&
                        endYear.equals(other.endYear);
            } else {
                return false;
            }
        }
    }
}
