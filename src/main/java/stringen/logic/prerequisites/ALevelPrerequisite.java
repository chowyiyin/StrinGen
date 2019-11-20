package stringen.logic.prerequisites;

public class ALevelPrerequisite implements Prerequisite {

    public static String PREFIX = "ASUB_PR";

    String minimumGrade;

    public ALevelPrerequisite(String minimumGrade) {
        this.minimumGrade = minimumGrade;
    }

    @Override
    public String generateString() {
        return PREFIX + appendSquareBrackets(minimumGrade);
    }

    public String appendSquareBrackets(String value) {
        return "[" + value + "]";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof ALevelPrerequisite) {
                ALevelPrerequisite other = (ALevelPrerequisite) o;
                return minimumGrade.equals(other.minimumGrade);
            } else {
                return false;
            }
        }
    }
}
