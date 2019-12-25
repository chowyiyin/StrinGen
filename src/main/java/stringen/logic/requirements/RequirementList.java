package stringen.logic.requirements;

import java.util.ArrayList;

import stringen.logic.StringGenerator;

public class RequirementList implements Requirement {

    private ArrayList<? extends Requirement> requirements;
    private int number = 1;
    private String prefix;

    public RequirementList(ArrayList<? extends Requirement> requirements, int number, String prefix) {
        this.requirements = requirements;
        this.number = number;
        this.prefix = prefix;
    }

    public RequirementList(ArrayList<? extends Requirement> requirements, String prefix) {
        this.requirements = requirements;
        this.prefix = prefix;
    }

    public ArrayList<? extends Requirement> getRequirements() {
        return requirements;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public String generateString() {
        StringBuilder string = new StringBuilder();
        string.append(number);
        requirements.stream().forEach(sub -> string.append("," + sub.generateString()));
        return prefix + StringGenerator.appendBrackets(string.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof RequirementList) {
                RequirementList other = (RequirementList) o;
                return requirements.equals(other.requirements)
                        && number == other.number
                        && prefix.equals(other.prefix);
            } else {
                return false;
            }
        }
    }
}
