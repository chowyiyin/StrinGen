package stringen.logic;

import java.util.ArrayList;

public class OrGroup extends Group {

    private ArrayList<AndGroup> andGroups = new ArrayList<>();

    public OrGroup(ArrayList<AndGroup> andGroups) {
        this.andGroups.addAll(andGroups);
    }

    public String generateString() {
        return StringGenerator.appendAndGroups(andGroups);
    }

    public boolean contains(OrGroup orGroup) {
        return andGroups.stream().anyMatch(andGroup ->
                orGroup.andGroups.stream().anyMatch(otherAndGroup -> otherAndGroup.equals(andGroup)));
    }

    public ArrayList<AndGroup> getAndGroups() {
        return andGroups;
    }

    public void removeAndGroups(ArrayList<AndGroup> andGroupsToRemove) {
        for (int i = 0; i < andGroupsToRemove.size(); i++) {
            andGroups.remove(andGroupsToRemove.get(i));
        }
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof OrGroup) {
            OrGroup other = (OrGroup) o;
            return andGroups.equals(other.andGroups);
        } else {
            return false;
        }
    }

}
