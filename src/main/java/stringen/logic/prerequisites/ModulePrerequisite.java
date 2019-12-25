package stringen.logic.prerequisites;

public class ModulePrerequisite extends ModuleRequirement {
    public final static String PREFIX = "MOD_PR";

    private String minimumGrade = "D";
    private String moduleCode;

    public ModulePrerequisite(String moduleCode, String minimumGrade) {
        super(PREFIX, moduleCode, minimumGrade);
        this.moduleCode = moduleCode;
        this.minimumGrade = minimumGrade;
    }

    @Override
    public String getRequirementType() {
        return PREFIX;
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
