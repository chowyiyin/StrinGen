package stringen.logic.requirements;

public class ModuleConcurrent implements Requirement {
    public static final String PREFIX = "MOD_CR";

    private String moduleCode;

    public ModuleConcurrent(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public String generateString() {
        return moduleCode;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof ModuleConcurrent) {
                ModuleConcurrent other = (ModuleConcurrent) o;
                return moduleCode.equals(other.moduleCode);
            } else {
                return false;
            }
        }
    }

}
