package stringen.logic.requirements;

public class MajorPrerequisite implements Requirement {
    public final static String PREFIX = "MAJ_PR";

    private String major;

    public MajorPrerequisite(String major) {
        this.major = major;
    }

    @Override
    public String generateString() {
        return major;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof MajorPrerequisite) {
                MajorPrerequisite other = (MajorPrerequisite) o;
                return major.equals(other.major);
            } else {
                return false;
            }
        }
    }

}
