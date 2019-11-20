package stringen.logic.prerequisites;

import java.util.ArrayList;

import stringen.logic.Cohort;

public class ModuleRequirementList implements Prerequisite {

    private ArrayList<ModuleRequirement> moduleRequirements = new ArrayList<>();
    private String prefix;
    private ArrayList<Cohort> cohorts = new ArrayList<>();

    public ModuleRequirementList(ArrayList<ModuleRequirement> moduleRequirements, String prefix) {
        this.moduleRequirements.addAll(moduleRequirements);
        this.prefix = prefix;
    }

    @Override
    public String generateString() {
        StringBuilder string = new StringBuilder();
        //TODO: implement the number
        string.append("1");
        moduleRequirements.stream().forEach(mod -> string.append("," + mod.generateString()));
        return prefix + appendBrackets(string.toString());
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof ModuleRequirementList) {
                ModuleRequirementList other = (ModuleRequirementList) o;
                return moduleRequirements.equals(other.moduleRequirements);
            } else {
                return false;
            }
        }
    }

}
