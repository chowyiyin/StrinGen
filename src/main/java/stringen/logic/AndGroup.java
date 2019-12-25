package stringen.logic;

import java.util.ArrayList;

public class AndGroup extends Group {
    private ArrayList<OrGroup> orGroups = new ArrayList<>();

    public AndGroup(ArrayList<OrGroup> orGroups) {
        this.orGroups.addAll(orGroups);
    }

    protected AndGroup() {}

    public String generateString() {
        if (orGroups.size() == 1) {
            return StringGenerator.appendOrGroups(orGroups);
        } else {
            return StringGenerator.appendBrackets(StringGenerator.appendOrGroups(orGroups));
        }
    }

    public ArrayList<OrGroup> getOrGroups() {
        return orGroups;
    }

    public boolean contains(AndGroup otherAndGroup) {
        ArrayList<OrGroup> otherOrGroups = otherAndGroup.getOrGroups();
        return orGroups.containsAll(otherOrGroups);
    }

    public void remove(AndGroup otherAndGroup) {
        ArrayList<OrGroup> otherOrGroups = otherAndGroup.getOrGroups();
        orGroups.removeAll(otherOrGroups);
    }

    public AndGroup getEmbeddedAndGroup(AndGroup otherAndGroup) {
        ArrayList<OrGroup> otherOrGroups = otherAndGroup.getOrGroups();
        ArrayList<OrGroup> similarOrGroups = new ArrayList<>();
        for (int i = 0; i < orGroups.size(); i++) {
            for (int j = 0; j < otherOrGroups.size(); j++) {
                OrGroup thisOrGroup = orGroups.get(i);
                OrGroup otherOrGroup = otherOrGroups.get(j);
                if (thisOrGroup.equals(otherOrGroup)) {
                    similarOrGroups.add(thisOrGroup);
                }
            }
        }

        if (similarOrGroups.isEmpty()) {
            return null;
        }

        return new AndGroup(similarOrGroups);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof AndGroup) {
            AndGroup other = (AndGroup) o;
            return orGroups.equals(other.orGroups);
        } else {
            return false;
        }
    }

    public boolean isEmpty() {
        return orGroups.isEmpty();
    }
}
