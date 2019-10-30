package stringen.logic;

import java.util.ArrayList;

import stringen.logic.prerequisites.McPrerequisite;
import stringen.logic.prerequisites.ModulePrerequisite;

public class Module {

    public static String FORMAT_MESSAGE = "%1$s is not a recognised module code.";
    public static String USAGE_MESSAGE = "Module codes have 2 prefix letters "
            + "and 4 preceding digits 0-9.\nExample: HY2103\nYou may want to check your input.";

    public static final String DATA_TYPE = "MODULE";


    private String moduleCode;
    private ArrayList<Cohort> cohorts = new ArrayList<>();

    // TODO: add attributes for other components

    public Module(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void addNewCohort(Cohort cohort) {
        cohorts.add(cohort);
    }

    public ArrayList<Cohort> getCohorts() {
        return cohorts;
    }

    // TODO: add setters and getters for other attributes
    @Override
    public String toString() {
        return moduleCode;
    }
}
