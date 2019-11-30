package stringen.logic;

import java.util.ArrayList;
import java.util.HashMap;

import stringen.exceptions.ParseModuleException;
import stringen.logic.prerequisites.ALevelPrerequisite;
import stringen.logic.prerequisites.CapPrerequisite;
import stringen.logic.prerequisites.MajorAntiRequisite;
import stringen.logic.prerequisites.MajorPrerequisite;
import stringen.logic.prerequisites.McPrerequisite;
import stringen.logic.prerequisites.ModuleAntirequisite;
import stringen.logic.prerequisites.ModulePrerequisite;
import stringen.logic.prerequisites.ModuleRequirement;
import stringen.logic.prerequisites.ModuleRequirementList;

public class Parser {
    private static final String moduleValidationRegex = "[A-Z]{2,3}\\d{4}[A-Z]?";
    private static final String FILLER_MODULE_CODE = "%";

    private HashMap<McPrerequisite, ArrayList<Cohort>> mcPrerequisites = new HashMap<>();
    private HashMap<ModulePrerequisite, ArrayList<Cohort>> modulePrerequisites = new HashMap();

    public Parser() {
    }

    public Cohort parseCohort(String startYear, String endYear) {
        String parsedStartYear = startYear.equals("-") ? "" : startYear;
        String parsedEndYear = endYear.equals("-") ? "" : endYear;
        return new Cohort(parsedStartYear, parsedEndYear);
    }

    public McPrerequisite parseMcPrerequisite(String mcs, Cohort cohort) {
        McPrerequisite mcPrerequisite = new McPrerequisite(mcs);
        if (mcPrerequisites.containsKey(mcPrerequisite)) {
            mcPrerequisites.get(mcPrerequisite).add(cohort);
        } else {
            ArrayList<Cohort> cohorts = new ArrayList<>();
            cohorts.add(cohort);
            mcPrerequisites.put(mcPrerequisite, cohorts);
        }
        return mcPrerequisite;
    }

    public ModuleRequirement parseModulePrerequisite(String module, String grade) {
        return new ModulePrerequisite(module, grade);
    }

    public ModuleAntirequisite parseModuleAntiPrerequisite(String module, String grade) {
        return new ModuleAntirequisite(module, grade);
    }

    public MajorPrerequisite parseMajorPrerequisite(String major) {
        return new MajorPrerequisite(major);
    }

    public MajorAntiRequisite parseMajorAntirequisite(String major) {
        return new MajorAntiRequisite(major);
    }

    public CapPrerequisite parseCap(String cap) {
        return new CapPrerequisite(cap);
    }

    public ALevelPrerequisite parseALevelPrerequisite(String module, String grade) {
        return new ALevelPrerequisite(module, grade);
    }

    public ModulePrerequisite parseModulePrerequisiteByMcs(String modulePrefix, String grade) {
        return new ModulePrerequisite(modulePrefix + FILLER_MODULE_CODE, grade);
    }

    public ModuleRequirementList parseModulePrerequisiteListByMcs(ArrayList<ModulePrerequisite> modulePrerequisites,
                                                                 int totalMcs) {
        return new ModuleRequirementList(modulePrerequisites, totalMcs / 4, ModulePrerequisite.PREFIX);
    }

    /**
     * Parses the user input for module codes.
     * @param userInput Input from user.
     * @return Formatted user input.
     */
    public static Module parseModule(String userInput) throws ParseModuleException {
        String moduleCode = userInput.toUpperCase();
        if (moduleCode.matches(moduleValidationRegex)) {
            return new Module(moduleCode);
        } else {
            throw new ParseModuleException(String.format(Module.FORMAT_MESSAGE, moduleCode));
        }
    }

    public static Module parseModuleOverridden(String userInput) {
        String moduleCode = userInput.toUpperCase();
        return new Module(moduleCode);
    }

}
