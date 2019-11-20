package stringen.logic.prerequisites;

public class ModuleRequirement implements Prerequisite {

    private String prefix;
    private String minimumGrade;
    private String moduleCode;

    public ModuleRequirement(String prefix) {
        this.prefix = prefix;
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
