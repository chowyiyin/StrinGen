package stringen.logic.requirements;

public class MajorPreclusion implements Requirement {
    public final static String PREFIX = "MAJ_AR";

    private String major;

    public MajorPreclusion(String major) {
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
            if (o instanceof MajorPreclusion) {
                MajorPreclusion other = (MajorPreclusion) o;
                return major.equals(other.major);
            } else {
                return false;
            }
        }
    }

}
