package stringen.logic;

import java.util.ArrayList;

public class Cohort {

    public static final String PREFIX_YEAR = "YEAR_PR";
    public static final String DEFAULT_DASH = "-";

    private String startYear;
    private String endYear;
    private ArrayList<OrGroup> orGroups = new ArrayList<>();

    public Cohort(String startYear, String endYear) {
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public void addOrGroups(ArrayList<OrGroup> orGroups) {
        this.orGroups.addAll(orGroups);
    }

    public ArrayList<OrGroup> getOrGroups() {
        return orGroups;
    }

    public String generateString() {
        return StringGenerator.generateStringForCohorts(startYear, endYear, orGroups);
    }

    public void removeOrGroups(ArrayList<OrGroup> similarOrGroups) {
        for (int i = 0; i < similarOrGroups.size(); i++) {
            OrGroup similarOrGroup = similarOrGroups.get(i);
            assert(orGroups.contains(similarOrGroup));
            orGroups.remove(similarOrGroup);
        }
    }

    public void removeAndGroupsFromOrGroup(OrGroup orGroup, ArrayList<AndGroup> ... andGroups) {
        assert(orGroups.contains(orGroup));
        for (int i = 0; i < orGroups.size(); i++) {
            OrGroup thisOrGroup = orGroups.get(i);
            if (thisOrGroup.equals(orGroup)) {
                for (int j = 0; j < andGroups.length; j++) {
                    thisOrGroup.removeAndGroups(andGroups[j]);
                }
                if (thisOrGroup.isEmpty()) {
                    orGroups.remove(thisOrGroup);
                }
            }
        }
    }

}
