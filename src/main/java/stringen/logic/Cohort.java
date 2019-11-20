package stringen.logic;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import stringen.logic.prerequisites.ALevelPrerequisiteList;
import stringen.logic.prerequisites.CapPrerequisite;
import stringen.logic.prerequisites.MajorRequirementList;
import stringen.logic.prerequisites.McPrerequisite;
import stringen.logic.prerequisites.ModuleRequirementList;

public class Cohort {
    public static String PREFIX = "YEAR_PR";

    private String startYear;
    private String endYear;
    private Optional<McPrerequisite> mcPrerequisite = Optional.empty();
    private ModuleRequirementList modulePrerequisites;
    private ModuleRequirementList moduleAntiRequisites;
    private MajorRequirementList majorAntiRequisites;
    private MajorRequirementList majorPrerequisites;
    private CapPrerequisite capPrerequisite;
    private ALevelPrerequisiteList aLevelPrerequisites;

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

    public void setModulePrerequisites(ModuleRequirementList modulePrerequisites) {
        this.modulePrerequisites = modulePrerequisites;
    }

    public void setModuleAntiRequisites(ModuleRequirementList moduleAntiRequisites) {
        this.moduleAntiRequisites = moduleAntiRequisites;
    }

    public void setCapPrerequisite(CapPrerequisite capPrerequisite) {
        this.capPrerequisite = capPrerequisite;
    }

    public void setMajorPrerequisites(MajorRequirementList majorPrerequisites) {
        this.majorPrerequisites = majorPrerequisites;
    }

    public void setMajorAntiRequisites(MajorRequirementList majorAntiRequisites) {
        this.majorAntiRequisites = majorAntiRequisites;
    }

    public void setALevelPrerequisites (ALevelPrerequisiteList aLevelPrerequisites) {
        this.aLevelPrerequisites = aLevelPrerequisites;
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
