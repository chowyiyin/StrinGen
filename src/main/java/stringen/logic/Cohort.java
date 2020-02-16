package stringen.logic;

import java.util.ArrayList;
import java.util.HashSet;

import stringen.logic.requirements.YearPrerequisite;

public class Cohort {

    public static final String PREFIX_YEAR = "YEAR_PR";
    public static final String DEFAULT_DASH = "-";

    private String startYear = "";
    private String endYear = "";
    private ArrayList<OrGroup> orGroups = new ArrayList<>();
    private SingleOrGroup yearRequirement = new SingleOrGroup(new YearPrerequisite());

    public Cohort(String startYear, String endYear) {
        this.startYear = startYear;
        this.endYear = endYear;
        this.yearRequirement = new SingleOrGroup(new YearPrerequisite(startYear, endYear));
        orGroups.add(yearRequirement);
    }

    public Cohort() {
    }

    public SingleOrGroup getYearRequirement() {
        return yearRequirement;
    }

    public SingleAndGroup getYearRequirementAsAndGroup() {
        return new SingleAndGroup(yearRequirement.getRequirement());
    }

    public void addOrGroups(ArrayList<OrGroup> orGroups) {
        for (int i = 0; i < orGroups.size(); i++) {
            OrGroup orGroup = orGroups.get(i);
            if (!orGroup.isEmpty()) {
                this.orGroups.add(orGroup);
            }
        }
    }

    public void addOrGroup(OrGroup orGroup) {
        if (!orGroup.isEmpty()) {
            this.orGroups.add(orGroup);
        }
    }

    public void removePureYearRequirements(int numberOfYearRequirements) {
        ArrayList<OrGroup> orGroupsToRemove = new ArrayList<>();
        for (int i = 0; i < orGroups.size(); i++) {
            OrGroup orGroup = orGroups.get(i);
            if (orGroup.onlyContainsYearRequirement(numberOfYearRequirements)) {
                orGroup.removeYearRequirements(numberOfYearRequirements);
                if (orGroup.isEmpty()) {
                    orGroupsToRemove.add(orGroup);
                }
            }
        }
        orGroups.removeAll(orGroupsToRemove);
    }

    public ArrayList<OrGroup> getOrGroups() {
        return orGroups;
    }

    public String generateString() {
        return StringGenerator.appendOrGroups(orGroups);
    }

    public void removeOrGroups(ArrayList<OrGroup> similarOrGroups) {
        for (int i = 0; i < similarOrGroups.size(); i++) {
            OrGroup similarOrGroup = similarOrGroups.get(i);
            assert(orGroups.contains(similarOrGroup));
            orGroups.remove(similarOrGroup);
        }
    }
}
