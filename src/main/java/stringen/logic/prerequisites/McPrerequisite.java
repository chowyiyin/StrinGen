package stringen.logic.prerequisites;

public class McPrerequisite implements Prerequisite {

    public static String PREFIX = "MC_PR";

    String numberOfMcs;

    public McPrerequisite(String numberOfMcs) {
        this.numberOfMcs = numberOfMcs;
    }


    @Override
    public String generateString() {
        return PREFIX + appendBrackets(numberOfMcs);
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
