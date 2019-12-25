package stringen.logic.prerequisites;

import stringen.logic.StringGenerator;

public class CoursePrerequisite implements Prerequisite {

    public static String PREFIX = "CRSE_PR";

    private String courseCode;

    public CoursePrerequisite(String courseCode) {
        this.courseCode = courseCode;
    }

    @Override
    public String generateString() {
        return PREFIX + StringGenerator.appendBrackets(courseCode);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof CoursePrerequisite) {
                CoursePrerequisite other = (CoursePrerequisite) o;
                return courseCode.equals(other.courseCode);
            } else {
                return false;
            }
        }
    }

}
