package stringen.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import stringen.exceptions.ParseModuleException;
import stringen.logic.prerequisites.McPrerequisite;
import stringen.logic.prerequisites.ModulePrerequisite;

public class Parser {
    private static String moduleValidationRegex = "[A-Z]{2,3}\\d{4}";

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

    public ModulePrerequisite parseModulePrerequisite(String module, String grade) {
        return new ModulePrerequisite(module, grade);
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
