package stringen.logic;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import stringen.logic.prerequisites.Prerequisite;

public class Group {

    private ArrayList<Group> groups = new ArrayList<>();
    private ArrayList<Prerequisite> prerequisites = new ArrayList<>();

    public Group() {
    }

    public void addPrerequisite(Prerequisite prerequisite) {
        requireNonNull(prerequisite);
        prerequisites.add(prerequisite);
    }

    public void addGroup(Group group) {
        requireNonNull(group);
        groups.add(group);
    }

    public ArrayList<Prerequisite> getLonePrerequisites() {
        return prerequisites;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public String generateString() {
        return StringGenerator.generateStringForGroups(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof Group) {
            Group other = (Group) o;
            return prerequisites.equals(other.prerequisites)
                    && groups.equals(other.groups);
        } else {
            return false;
        }
    }
}
