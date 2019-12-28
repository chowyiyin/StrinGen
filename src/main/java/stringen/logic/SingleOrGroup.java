package stringen.logic;

import stringen.logic.requirements.Requirement;
import stringen.logic.requirements.YearPrerequisite;

public class SingleOrGroup extends OrGroup {
    private Requirement requirement;

    public SingleOrGroup(Requirement requirement) {
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
        if (this == o) {
            return true;
        } else if (o instanceof SingleOrGroup) {
            SingleOrGroup other = (SingleOrGroup) o;
            return requirement.equals(other.requirement);
        } else {
            return false;
        }
    }
}
