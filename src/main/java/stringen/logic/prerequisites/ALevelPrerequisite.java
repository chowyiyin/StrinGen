package stringen.logic.prerequisites;

public class ALevelPrerequisite implements Prerequisite {

    public static String PREFIX = "ASUB_PR";

    private String subject;
    private String minimumGrade;

    public ALevelPrerequisite(String subject, String minimumGrade) {
        this.subject = subject;
        this.minimumGrade = minimumGrade;
    }

    @Override
    public String generateString() {
        return subject + appendSquareBrackets(minimumGrade);
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
