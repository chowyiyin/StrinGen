package stringen.logic.prerequisites;

public class ModuleRequirement implements Prerequisite {

    public static final String DEFAULT_GRADE = "D";

    private String prefix;
    private String minimumGrade;
    private String moduleCode;

    public ModuleRequirement(String prefix, String moduleCode, String minimumGrade) {
        this.prefix = prefix;
        this.moduleCode = moduleCode;
        this.minimumGrade = minimumGrade;
    }

    @Override
    public String generateString() {
        return moduleCode + appendSquareBrackets(minimumGrade);
    }

    public String appendSquareBrackets(String value) {
        return "[" + value + "]";
    }

    public String getRequirementType() {
        return prefix;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof ModuleRequirement) {
                ModuleRequirement other = (ModuleRequirement) o;
                return minimumGrade.equals(other.minimumGrade)
                        && moduleCode.equals(other.moduleCode);
            } else {
                return false;
            }
        }
    }

}
