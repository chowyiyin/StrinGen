package stringen.logic.prerequisites;

public class ModulePreclusion extends ModuleRequirement {
    public final static String PREFIX = "MOD_AR";

    private String minimumGrade;
    private String moduleCode;

    public ModulePreclusion(String moduleCode, String minimumGrade) {
        super(PREFIX, moduleCode, minimumGrade);
        this.moduleCode = moduleCode;
        this.minimumGrade = minimumGrade;
    }

    @Override public String getRequirementType() {
        return PREFIX;
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
