package stringen.logic;

import java.util.ArrayList;

public class Cohort {

    private String startYear;
    private String endYear;
    private ArrayList<OrGroup> orGroups = new ArrayList<>();

    public Cohort(String startYear, String endYear) {
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public void addOrGroup(OrGroup orGroup) {
        orGroups.add(orGroup);
    }

    public ArrayList<OrGroup> getOrGroups() {
        return orGroups;
    }

    public String generateString() {
        return StringGenerator.appendOrGroups(orGroups);
    }

    public boolean contains(OrGroup group) {
        return orGroups.stream().anyMatch(orGroup -> orGroup.contains(group));
    }

    public void removeOrGroups(ArrayList<OrGroup> similarOrGroups) {
        for (int i = 0; i < similarOrGroups.size(); i++) {
            OrGroup similarOrGroup = (OrGroup) similarOrGroups.get(i);
            assert(orGroups.contains(similarOrGroup));
            orGroups.remove(similarOrGroup);
        }
    }

    public void removeAndGroupsFromOrGroup(OrGroup orGroup, ArrayList<AndGroup> andGroups) {
        assert(orGroups.contains(orGroup));
        for (int i = 0; i < orGroups.size(); i++) {
            if (orGroups.get(i).equals(orGroup)) {
                orGroups.get(i).removeAndGroups(andGroups);
            }
        }
    }

}
