package stringen.logic.requirements;

public class ALevelPrerequisite implements Requirement {

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
