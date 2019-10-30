package stringen.logic.prerequisites;

import java.util.ArrayList;

import stringen.logic.Cohort;

public class ModulePrerequisite implements Prerequisite {
    public static String PREFIX = "MOD_PR";

    private String minimumGrade;
    private String moduleCode;
    private ArrayList<Cohort> cohorts = new ArrayList<>();

    public ModulePrerequisite(String moduleCode, String minimumGrade) {
        this.moduleCode = moduleCode;
        this.minimumGrade = minimumGrade;
    }

    @Override
    public void addCohort(Cohort cohort) {
        cohorts.add(cohort);
    }

    @Override
    public String generateString() {
        return moduleCode + appendSquareBrackets(minimumGrade);
    }

    public String appendSquareBrackets(String value) {
        return "[" + value + "]";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof ModulePrerequisite) {
                ModulePrerequisite other = (ModulePrerequisite) o;
                return minimumGrade.equals(other.minimumGrade)
                        && moduleCode.equals(other.moduleCode);
            } else {
                return false;
            }
        }
    }

}
