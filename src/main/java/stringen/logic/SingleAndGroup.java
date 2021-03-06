package stringen.logic;

import stringen.logic.requirements.Requirement;
import stringen.logic.requirements.YearPrerequisite;

public class SingleAndGroup extends AndGroup {
    private Requirement requirement;

    public SingleAndGroup(Requirement requirement) {
        super();
        this.requirement = requirement;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public boolean isYearRequirement() {
        return requirement instanceof YearPrerequisite;
    }

    @Override
    public String generateString() {
        return requirement.generateString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof SingleAndGroup) {
            SingleAndGroup other = (SingleAndGroup) o;
            return requirement.equals(other.requirement);
        } else {
            return false;
        }
    }
}
