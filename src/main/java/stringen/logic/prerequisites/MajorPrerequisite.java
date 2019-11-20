package stringen.logic.prerequisites;

public class MajorPrerequisite extends MajorRequirement {
    public final static String PREFIX = "MAJ_PR";

    private String major;

    public MajorPrerequisite(String major) {
        super(PREFIX);
        this.major = major;
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
