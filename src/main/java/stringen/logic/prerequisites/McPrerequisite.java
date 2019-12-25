package stringen.logic.prerequisites;

import stringen.logic.StringGenerator;

public class McPrerequisite implements Prerequisite {

    public static String PREFIX = "MC_PR";

    private String numberOfMcs;
    private String modulePrefix;

    public McPrerequisite(String numberOfMcs, String modulePrefix) {
        this.numberOfMcs = numberOfMcs;
        this.modulePrefix = modulePrefix;
    }

    //todo: add module prefixes to generated string
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
