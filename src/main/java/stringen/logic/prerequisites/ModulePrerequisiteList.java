package stringen.logic.prerequisites;

import java.util.ArrayList;
import java.util.Arrays;

import stringen.logic.Cohort;

public class ModulePrerequisiteList implements Prerequisite {
    public static String PREFIX = "MOD_PR";

    private ArrayList<ModulePrerequisite> modulePrerequisites = new ArrayList<>();
    private ArrayList<Cohort> cohorts = new ArrayList<>();

    public ModulePrerequisiteList(ArrayList<ModulePrerequisite> modulePrerequisites) {
        this.modulePrerequisites.addAll(modulePrerequisites);
    }

    @Override
    public void addCohort(Cohort cohort) {
        cohorts.add(cohort);
    }

    @Override
    public String generateString() {
        StringBuilder string = new StringBuilder();
        //TODO: implement the number
        string.append("1");
        modulePrerequisites.stream().forEach(mod -> string.append("," + mod.generateString()));
        return PREFIX + appendBrackets(string.toString());
    }

    public String appendSquareBrackets(String value) {
        return "[" + value + "]";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof ModulePrerequisiteList) {
                ModulePrerequisiteList other = (ModulePrerequisiteList) o;
                return modulePrerequisites.equals(other.modulePrerequisites);
            } else {
                return false;
            }
        }
    }

}
