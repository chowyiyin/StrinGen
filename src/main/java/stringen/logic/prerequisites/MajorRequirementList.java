package stringen.logic.prerequisites;

import java.util.ArrayList;

import stringen.logic.StringGenerator;

public class MajorRequirementList implements Prerequisite {

    private ArrayList<MajorRequirement> majorRequirements = new ArrayList<>();
    private int numberOfMajors;
    private String prefix;

    public MajorRequirementList(ArrayList<MajorRequirement> majorRequirements, int numberOfMajors, String prefix) {
        this.majorRequirements.addAll(majorRequirements);
        this.numberOfMajors = numberOfMajors;
        this.prefix = prefix;
    }

    public MajorRequirementList() {
    }

    public ArrayList<MajorRequirement> getMajorRequirements() {
        return majorRequirements;
    }

    public boolean isEmpty() {
        return majorRequirements.size() == 0;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public String generateString() {
        StringBuilder string = new StringBuilder();
        string.append(numberOfMajors);
        majorRequirements.stream().forEach(major -> string.append("," + major.generateString()));
        return prefix + StringGenerator.appendBrackets(string.toString());
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
