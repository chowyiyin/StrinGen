package stringen.logic.prerequisites;

public class MajorAntiRequisite extends MajorRequirement {
    public final static String PREFIX = "MAJ_AR";

    private String major;

    public MajorAntiRequisite(String major) {
        super(PREFIX, major);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof MajorAntiRequisite) {
                MajorAntiRequisite other = (MajorAntiRequisite) o;
                return major.equals(other.major);
            } else {
                return false;
            }
        }
    }

}
