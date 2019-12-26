package stringen.logic.requirements;

import stringen.logic.StringGenerator;

public class YearPrerequisite implements Requirement {

    public static String PREFIX = "YEAR_PR";

    private String startYear;
    private String endYear;

    public YearPrerequisite(String startYear, String endYear) {
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
            if (o instanceof YearPrerequisite) {
                YearPrerequisite other = (YearPrerequisite) o;
                return startYear.equals(other.startYear) &&
                        endYear.equals(other.endYear);
            } else {
                return false;
            }
        }
    }
}
