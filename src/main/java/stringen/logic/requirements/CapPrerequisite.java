package stringen.logic.requirements;

import stringen.logic.StringGenerator;

public class CapPrerequisite implements Requirement {

    public static String PREFIX = "CAP_PR";

    String cap;

    public CapPrerequisite(String cap) {
        this.cap = cap;
    }

    @Override
    public String generateString() {
        return PREFIX + StringGenerator.appendBrackets(cap);
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
