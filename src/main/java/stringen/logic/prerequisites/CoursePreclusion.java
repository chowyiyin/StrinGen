package stringen.logic.prerequisites;

import stringen.logic.StringGenerator;

public class CoursePreclusion implements Prerequisite {

    public static String PREFIX = "CRSE_AR";

    private String courseCode;

    public CoursePreclusion(String courseCode) {
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
            if (o instanceof CoursePreclusion) {
                CoursePreclusion other = (CoursePreclusion) o;
                return courseCode.equals(other.courseCode);
            } else {
                return false;
            }
        }
    }

}
