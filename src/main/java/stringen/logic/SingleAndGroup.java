package stringen.logic;

import stringen.logic.prerequisites.Prerequisite;

public class SingleAndGroup extends AndGroup {
    private Prerequisite prerequisite;

    public SingleAndGroup(Prerequisite prerequisite) {
        super();
        this.prerequisite = prerequisite;
    }

    public Prerequisite getPrerequisite() {
        return prerequisite;
    }

    @Override
    public String generateString() {
        return prerequisite.generateString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof SingleAndGroup) {
            SingleAndGroup other = (SingleAndGroup) o;
            return prerequisite.equals(other.prerequisite);
        } else {
            return false;
        }
    }
}
