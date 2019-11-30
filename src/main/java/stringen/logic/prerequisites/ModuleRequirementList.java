package stringen.logic.prerequisites;

import java.util.ArrayList;

import stringen.logic.Cohort;

public class ModuleRequirementList implements Prerequisite {

    private ArrayList<ModuleRequirement> moduleRequirements = new ArrayList<>();
    private int numberOfModules;
    private String prefix;
    private ArrayList<Cohort> cohorts = new ArrayList<>();

    public ModuleRequirementList(ArrayList<? extends ModuleRequirement> moduleRequirements,
                                 int numberOfModules, String prefix) {
        this.moduleRequirements.addAll(moduleRequirements);
        this.numberOfModules = numberOfModules;
        this.prefix = prefix;
    }

    public ModuleRequirementList() {
    }

    public boolean isEmpty() {
        return moduleRequirements.size() == 0;
    }

    @Override
    public String generateString() {
        StringBuilder string = new StringBuilder();
        string.append(numberOfModules);
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
