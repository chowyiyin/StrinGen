package stringen.logic.requirements;

import stringen.logic.StringGenerator;

public class McPrerequisite implements Requirement {

    public static String PREFIX = "MC_PR";

    private String numberOfMcs;

    public McPrerequisite(String numberOfMcs) {
        this.numberOfMcs = numberOfMcs;
    }

    @Override
    public String generateString() {
        return PREFIX + StringGenerator.appendBrackets(numberOfMcs);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof McPrerequisite) {
                McPrerequisite other = (McPrerequisite) o;
                return numberOfMcs.equals(other.numberOfMcs);
            } else {
                return false;
            }
        }
    }
}
