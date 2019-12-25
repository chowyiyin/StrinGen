package stringen.logic.prerequisites;

public class MajorPreclusion extends MajorRequirement {
    public final static String PREFIX = "MAJ_AR";

    private String major;

    public MajorPreclusion(String major) {
        super(PREFIX, major);
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
