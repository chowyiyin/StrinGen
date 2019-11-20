package stringen.logic.prerequisites;

public class CapPrerequisite implements Prerequisite {

    public static String PREFIX = "CAP_PR";

    String cap;

    public CapPrerequisite(String cap) {
        this.cap = cap;
    }

    @Override
    public String generateString() {
        return PREFIX + appendBrackets(cap);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof CapPrerequisite) {
                CapPrerequisite other = (CapPrerequisite) o;
                return cap.equals(other.cap);
            } else {
                return false;
            }
        }
    }
}
