package stringen.logic;

import stringen.exceptions.ParseModuleException;

public class Parser {
    private static String moduleValidationRegex = "[A-Z]{2}\\d{4}";

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
