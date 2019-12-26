package stringen.logic;

import java.util.ArrayList;

public class OrGroup extends Group {

    private ArrayList<AndGroup> andGroups = new ArrayList<>();

    public OrGroup(ArrayList<AndGroup> andGroups) {
        this.andGroups.addAll(andGroups);
    }

    protected OrGroup() {}

    public String generateString() {
        if (andGroups.size() == 0) {
            return "";
        } else if (andGroups.size() == 1) {
            return StringGenerator.appendAndGroups(andGroups);
        } else {
            return StringGenerator.appendBrackets(StringGenerator.appendAndGroups(andGroups));
        }
    }

    public ArrayList<AndGroup> getEmbeddedAndGroups(OrGroup otherGroup) {
        ArrayList<AndGroup> otherAndGroups = otherGroup.getAndGroups();
        ArrayList<AndGroup> embeddedAndGroups = new ArrayList<>();
        for (int i = 0; i < andGroups.size(); i++) {
            for (int j = 0; j < otherAndGroups.size(); j++) {
                AndGroup thisAndGroup = andGroups.get(i);
                AndGroup otherAndGroup = otherAndGroups.get(j);
                AndGroup embeddedAndGroup = thisAndGroup.getEmbeddedAndGroup(otherAndGroup);
                if (embeddedAndGroup != null && !embeddedAndGroup.equals(thisAndGroup)) {
                    embeddedAndGroups.add(embeddedAndGroup);
                }
            }
        }
        return embeddedAndGroups;
    }


    public ArrayList<AndGroup> getEqualAndGroups(OrGroup otherGroup) {
        ArrayList<AndGroup> otherAndGroups = otherGroup.getAndGroups();
        ArrayList<AndGroup> equalAndGroups = new ArrayList<>();
        for (int i = 0; i < andGroups.size(); i++) {
            for (int j = 0; j < otherAndGroups.size(); j++) {
                AndGroup thisAndGroup = andGroups.get(i);
                AndGroup otherAndGroup = otherAndGroups.get(j);
                if (thisAndGroup.equals(otherAndGroup)) {
                    equalAndGroups.add(copyAndGroup(thisAndGroup));
                }
            }
        }
        return equalAndGroups;
    }

    public AndGroup copyAndGroup(AndGroup andGroup) {
        return new AndGroup(andGroup.getOrGroups());
    }

    public ArrayList<AndGroup> getAndGroups() {
        return andGroups;
    }

    public void removeAndGroups(ArrayList<AndGroup> andGroupsToRemove) {
        for (int i = 0; i < andGroups.size(); i++) {
            for (int j = 0; j < andGroupsToRemove.size(); j++) {
                AndGroup andGroup = andGroups.get(i);
                AndGroup andGroupToRemove = andGroupsToRemove.get(j);
                if (andGroup.contains(andGroupToRemove)) {
                    andGroup.remove(andGroupToRemove);
                    if (andGroup.isEmpty()) {
                        andGroups.remove(andGroup);
                    }
                }
            }
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

    public boolean isEmpty() {
        return andGroups.isEmpty();
    }

}
