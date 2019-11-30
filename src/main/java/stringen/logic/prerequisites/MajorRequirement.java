package stringen.logic.prerequisites;

public class MajorRequirement implements Prerequisite {

    private String prefix;
    private String major;

    public MajorRequirement(String prefix, String major) {
        this.prefix = prefix;
        this.major = major;
    }

    @Override
    public String generateString() {
        return major;
    }

    public String appendSquareBrackets(String value) {
        return "[" + value + "]";
    }

    public String getRequirementType() {
        return prefix;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof MajorRequirement) {
                MajorRequirement other = (MajorRequirement) o;
                return major.equals(other.major)
                        && prefix.equals(other.prefix);
            } else {
                return false;
            }
        }
    }

}
