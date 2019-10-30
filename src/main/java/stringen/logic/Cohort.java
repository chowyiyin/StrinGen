package stringen.logic;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Optional;

import stringen.logic.prerequisites.McPrerequisite;
import stringen.logic.prerequisites.ModulePrerequisite;
import stringen.logic.prerequisites.ModulePrerequisiteList;

public class Cohort {
    public static String PREFIX = "YEAR_PR";

    private String startYear;
    private String endYear;
    private Optional<McPrerequisite> mcPrerequisite = Optional.empty();
    private ModulePrerequisiteList modulePrerequisites;

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

    public void setMcPrerequisite(McPrerequisite mcPrerequisite) {
        this.mcPrerequisite = Optional.of(mcPrerequisite);
    }

    public void setModulePrerequisites(ModulePrerequisiteList modulePrerequisites) {
        this.modulePrerequisites = modulePrerequisites;
    }

    public String getFormattedYearRange() {
        return PREFIX + "(" + startYear + "," + endYear + ")";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFormattedYearRange())
                .append("&")
                .append(mcPrerequisite.isEmpty() ? "" : mcPrerequisite.get().generateString())
                .append("&" + modulePrerequisites.generateString());
        return sb.toString();
    }

}
