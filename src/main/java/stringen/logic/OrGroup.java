package stringen.logic;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class OrGroup extends Group {

    private ArrayList<AndGroup> andGroups = new ArrayList<>();

    public OrGroup(ArrayList<AndGroup> andGroups) {
        this.andGroups.addAll(andGroups);
    }

    protected OrGroup() {}

    /**
     * Generates the string for an <code>OrGroup</code>.
     * @return String of this object.
     */
    public String generateString() {
        if (andGroups.size() == 0) {
            return "";
        } else if (andGroups.size() == 1) {
            return StringGenerator.appendAndGroups(andGroups);
        } else {
            return StringGenerator.appendBrackets(StringGenerator.appendAndGroups(andGroups));
        }
    }

    public boolean isYearRequirement() {
        return false;
    }

    /**
     * Returns the biggest <code>AndGroup</code> that is common between this object and the other <code>OrGroup</code>.
     * @param otherGroup Other <code>OrGroup</code>.
     * @return <code>AndGroup</code> that is common between both groups.
     */
    public AndGroup getBiggestEmbeddedAndGroups(OrGroup otherGroup) {
        boolean onlyCanExtractWholeAndGroups = this.andGroups.size() > 1 || otherGroup.andGroups.size() > 1;
        if (onlyCanExtractWholeAndGroups) {
            return extractEquals(otherGroup);
        } else {
            // both only have one and group
            AndGroup firstAndGroup = andGroups.size() == 1 ? andGroups.get(0) : new AndGroup();
            AndGroup secondAndGroup = otherGroup.getAndGroups().size() == 1 ? otherGroup.getAndGroups().get(0) :
                    new AndGroup();
            return extractEmbeddedGroups(firstAndGroup, secondAndGroup);
        }
    }

    private AndGroup extractEquals(OrGroup otherGroup) {
        ArrayList<AndGroup> otherAndGroups = otherGroup.getAndGroups();
        PriorityQueue<AndGroup> embeddedAndGroups = new PriorityQueue<>(new AndGroupComparator());
        for (int i = 0; i < andGroups.size(); i++) {
            for (int j = 0; j < otherAndGroups.size(); j++) {
                AndGroup thisAndGroup = andGroups.get(i);
                AndGroup otherAndGroup = otherAndGroups.get(j);
                if (thisAndGroup.equals(otherAndGroup)) {
                    embeddedAndGroups.add(copyAndGroup(thisAndGroup));
                }
            }
        }
        return embeddedAndGroups.peek() == null ? new AndGroup() : embeddedAndGroups.poll();
    }

    private AndGroup extractEmbeddedGroups(AndGroup firstAndGroup, AndGroup secondAndGroup) {
        return firstAndGroup.getEmbeddedAndGroup(secondAndGroup);
    }

    private AndGroup copyAndGroup(AndGroup andGroup) {
        return new AndGroup(andGroup.getOrGroups());
    }

    /**
     * Returns an <code>AndGroup</code> that contains an <code>OrGroup</code> of the remaining <code>AndGroup</code>s
     * and the <code>YearRequirement</code> of the input cohort.
     * @param andGroup <code>AndGroup</code> that will be extracted.
     * @param cohort Cohort that contains this object.
     * @return <code>AndGroup</code> that contains the remaining information.
     */
    public AndGroup getRemainingAndGroup(AndGroup andGroup, Cohort cohort) {
        if (!andGroups.contains(andGroup)) {
            // andGroup is embedded
            AndGroup remainingAndGroup = andGroups.get(0).getRemainingAndGroup(andGroup);
            remainingAndGroup.addOrGroup(cohort.getYearRequirement());
            return remainingAndGroup;
        } else {
            // andGroup was extracted whole
            ArrayList<AndGroup> remainingAndGroups = new ArrayList<>(andGroups);
            remainingAndGroups.remove(andGroup);
            ArrayList<OrGroup> orGroupsForAndGroup = new ArrayList<>();
            orGroupsForAndGroup.add(new OrGroup(remainingAndGroups));
            orGroupsForAndGroup.add(cohort.getYearRequirement());
            return new AndGroup(orGroupsForAndGroup);
        }
    }

    public ArrayList<AndGroup> getAndGroups() {
        return andGroups;
    }


    public boolean isEmpty() {
        return andGroups.isEmpty();
    }

    @Override
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
