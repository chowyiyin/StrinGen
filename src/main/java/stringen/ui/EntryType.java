package stringen.ui;

public enum EntryType {
    MOD_PREREQ("Module Prerequisite"),
    COURSE_PREREQ("Course Prerequisite"),
    MC_PREREQ("MC Prerequisite"),
    MAJOR_PREREQ("Major Prerequisite"),
    CAP_PREREQ("CAP Prerequisite"),
    A_LEVEL_PREREQ("A-Level Prerequisite"),
    COURSE_PRECLUSION("Course Preclusion"),
    MODULE_PRECLUSION("Module Preclusion"),
    MAJOR_PRECLUSION("Major Preclusion"),
    CONCURRENT_MODULE("Concurrent Module");

    private String name;

    EntryType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static EntryType getEntryType(String entryTypeName) {
        for (EntryType entryType: values()) {
            if (entryType.getName().equals(entryTypeName)) {
                return entryType;
            }
        }
        throw new IllegalArgumentException();
    }

}
