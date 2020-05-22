package stringen.logic.requirements;

import java.util.ArrayList;

public class ALevelPrerequisite implements Requirement {

    public static String PREFIX = "ASUB_PR";

    private String subjectCode;
    private String minimumGrade;

    public ALevelPrerequisite(String subjectCode, String minimumGrade) {
        this.subjectCode = subjectCode;
        this.minimumGrade = minimumGrade;
    }

    @Override
    public String generateString() {
        return subjectCode + appendSquareBrackets(minimumGrade);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof ALevelPrerequisite) {
                ALevelPrerequisite other = (ALevelPrerequisite) o;
                return subjectCode == other.subjectCode &&
                        minimumGrade.equals(other.minimumGrade);
            } else {
                return false;
            }
        }
    }
}
