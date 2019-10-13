import java.util.ArrayList;

public class Module {

    private String moduleCode;
    private ArrayList<Cohort> cohorts = new ArrayList<Cohort>();

    // TODO: add attributes for other components

    public Module(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void addNewCohort(String start, String end) {
        cohorts.add(new Cohort(start, end));
    }

    // TODO: add setters and getters for other attributes

}
