package stringen.logic.requirements;

public class ModulePreclusion implements Requirement {
    public final static String PREFIX = "MOD_AR";
    public static final String DEFAULT_GRADE = "D";

    private String minimumGrade;
    private String moduleCode;

    public ModulePreclusion(String moduleCode, String minimumGrade) {
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
            if (o instanceof ModulePreclusion) {
                ModulePreclusion other = (ModulePreclusion) o;
                return minimumGrade.equals(other.minimumGrade)
                        && moduleCode.equals(other.moduleCode);
            } else {
                return false;
            }
        }
    }

}
