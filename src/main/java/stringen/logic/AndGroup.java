package stringen.logic;

import java.util.ArrayList;

public class AndGroup extends Group {
    private ArrayList<OrGroup> orGroups = new ArrayList<>();

    public AndGroup(ArrayList<OrGroup> orGroups) {
        this.orGroups.addAll(orGroups);
    }

    protected AndGroup() {}

    public void addOrGroup(OrGroup orGroup) {
        this.orGroups.add(orGroup);
    }

    public String generateString() {
        if (orGroups.size() == 0) {
            return "";
        } else if (orGroups.size() == 1) {
            return StringGenerator.appendOrGroups(orGroups);
        } else {
            return StringGenerator.appendBrackets(StringGenerator.appendOrGroups(orGroups));
        }
    }

    public ArrayList<OrGroup> getOrGroups() {
        return orGroups;
    }

    public int size() {
        return orGroups.size();
    }

    /**
     * Obtains the similar <code>OrGroup</code>s between this object and the other <code>AndGroup</code>.
     * @param otherAndGroup Other <code>AndGroup</code>.
     * @return <code>AndGroup</code> that contains the common <code>OrGroup</code>s.
     */
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
        return new AndGroup(similarOrGroups);
    }

    /**
     * Gets the remaining <code>OrGroups</code> after extracting the embeddedAndGroup.
     * @param embeddedAndGroup <code>AndGroup</code> that is a subset of this object.
     * @return New <code>AndGroup</code> that contains the remaining <code>OrGroup</code>s.
     */
    public AndGroup getRemainingAndGroup(AndGroup embeddedAndGroup) {
        ArrayList<OrGroup> embeddedOrGroups = embeddedAndGroup.getOrGroups();
        ArrayList<OrGroup> remainingOrGroups = new ArrayList<>(orGroups);
        remainingOrGroups.removeAll(embeddedOrGroups);
        return new AndGroup(remainingOrGroups);
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
