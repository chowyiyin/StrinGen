package stringen.logic;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

public class Cohort {
    public static String PREFIX = "YEAR_PR";

    private String startYear;
    private String endYear;

    private ArrayList<Group> groups = new ArrayList<>();

    public Cohort(String startYear, String endYear) {
        requireNonNull(startYear, endYear);
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public String getStartYear() {
        return startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public String getFormattedYearRange() {
        return PREFIX + "(" + startYear + "," + endYear + ")";
    }

    public ArrayList<Group> getSimilarities(Cohort otherCohort) {
        ArrayList<Group> similarities = new ArrayList<>();
        for (int i = 0; i < groups.size(); i++) {
            Group group = groups.get(i);
            if (otherCohort.getGroups().contains(group)) {
                similarities.add(group);
            }
        }
        return similarities;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void extract(Group group) {
        for (int i = 0; i < groups.size(); i++) {
            if (group.equals(groups.get(i))) {
                groups.remove(group);
            }
        }
    }

    public String generateString() {
        return StringGenerator.generateStringForCohorts(groups);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof  Cohort) {
            Cohort other = (Cohort) o;
            return startYear.equals(other.startYear)
                    && endYear.equals(other.endYear)
                    && groups.equals(other.groups);
        } else {
            return false;
        }
    }

}
