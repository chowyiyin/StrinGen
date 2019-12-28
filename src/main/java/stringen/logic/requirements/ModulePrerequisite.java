package stringen.logic.requirements;

public class ModulePrerequisite implements Requirement {
    public static final String PREFIX = "MOD_PR";
    public static final String DEFAULT_GRADE = "D";

    private String minimumGrade = "D";
    private String moduleCode;


    public ModulePrerequisite(String moduleCode, String minimumGrade) {
        this.moduleCode = moduleCode;
        this.minimumGrade = minimumGrade;
    }

    @Override
    public String generateString() {
        return moduleCode + appendSquareBrackets(minimumGrade);
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
