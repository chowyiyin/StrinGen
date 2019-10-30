package stringen;

import java.util.ArrayList;

public class Util {

    public static final ArrayList<String> YEARS = new ArrayList<>();
    public static final ArrayList<String> GRADES = new ArrayList<>();

    public static void initialise() {
        initialiseYears();
        initialiseGrades();
    }

    public static void initialiseYears() {
        YEARS.add("-");
        for (int i = 2013; i <= 2050; i++) {
            YEARS.add("" + i);
        }
    }

    public static void initialiseGrades() {
        for (char i = 'A'; i <= 'F'; i++) {
            GRADES.add(Character.toString(i));
        }
    }
}
