package stringen.logic.requirements;

import stringen.logic.StringGenerator;

public class YearPrerequisite implements Requirement {

    public static String PREFIX = "YEAR_PR";

    private String startYear = "";
    private String endYear = "";

    public YearPrerequisite(String startYear, String endYear) {
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public YearPrerequisite() {
    }

    public String getStartYear() {
        return startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    @Override
    public String generateString() {
        if (!startYear.equals("") || !endYear.equals("")) {
            return PREFIX + StringGenerator.appendBrackets(startYear + "," + endYear);
        } else {
            return "";
        }
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
