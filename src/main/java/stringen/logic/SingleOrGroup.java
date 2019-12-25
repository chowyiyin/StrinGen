package stringen.logic;

import stringen.logic.prerequisites.Prerequisite;

public class SingleOrGroup extends OrGroup {
    private Prerequisite prerequisite;

    public SingleOrGroup(Prerequisite prerequisite) {
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
        if (this == o) {
            return true;
        } else if (o instanceof SingleOrGroup) {
            SingleOrGroup other = (SingleOrGroup) o;
            return prerequisite.equals(other.prerequisite);
        } else {
            return false;
        }
    }
}
