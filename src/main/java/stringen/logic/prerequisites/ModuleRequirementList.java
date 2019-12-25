package stringen.logic.prerequisites;

import java.util.ArrayList;

import stringen.logic.Cohort;
import stringen.logic.StringGenerator;

public class ModuleRequirementList implements Prerequisite {

    private ArrayList<ModuleRequirement> moduleRequirements = new ArrayList<>();
    private int numberOfModules;
    private String prefix;
    private ArrayList<Cohort> cohorts = new ArrayList<>();

    public ModuleRequirementList(ArrayList<ModuleRequirement> moduleRequirements, int numberOfModules, String prefix) {
        this.moduleRequirements.addAll(moduleRequirements);
        this.numberOfModules = numberOfModules;
        this.prefix = prefix;
    }

    public ModuleRequirementList() {
    }

    public ArrayList<ModuleRequirement> getModuleRequirements() {
        return moduleRequirements;
    }

    public boolean isEmpty() {
        return moduleRequirements.size() == 0;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public String generateString() {
        StringBuilder string = new StringBuilder();
        string.append(numberOfModules);
        moduleRequirements.stream().forEach(mod -> string.append("," + mod.generateString()));
        return prefix + StringGenerator.appendBrackets(string.toString());
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
