package stringen.logic.prerequisites;

import java.util.ArrayList;

public class MajorRequirementList implements Prerequisite {
    public static String PREFIX = "MAJ_PR";

    private ArrayList<MajorRequirement> majorRequirements = new ArrayList<>();
    private ArrayList<Cohort> cohorts = new ArrayList<>();

    public MajorRequirementList(ArrayList<MajorRequirement> majorRequirements) {
        this.majorRequirements.addAll(majorRequirements);
    }

    public MajorRequirementList() {
    }

    public boolean isEmpty() {
        return majorRequirements.size() == 0;
    }

    @Override
    public String generateString() {
        StringBuilder string = new StringBuilder();
        //TODO: implement the number
        string.append("1");
        majorRequirements.stream().forEach(major -> string.append("," + major.generateString()));
        return PREFIX + appendBrackets(string.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof MajorRequirementList) {
                MajorRequirementList other = (MajorRequirementList) o;
                return majorRequirements.equals(other.majorRequirements);
            } else {
                return false;
            }
        }
    }

}
