package stringen;

import java.util.ArrayList;

import stringen.logic.Cohort;

public class Util {

    public static final ArrayList<String> YEARS = new ArrayList<>();
    public static final ArrayList<String> GRADES = new ArrayList<>();
    public static final ArrayList<String> CAP = new ArrayList<>();

    public static void initialise() {
        initialiseYears();
        initialiseGrades();
        initialiseCap();
    }

    public static void initialiseYears() {
        YEARS.add(Cohort.DEFAULT_DASH);
        for (int i = 2013; i <= 2050; i++) {
            YEARS.add("" + i);
        }
    }

    public static void initialiseGrades() {
        for (char i = 'A'; i <= 'F'; i++) {
            GRADES.add(Character.toString(i));
        }
    }

    public static void initialiseCap() {
        for (double i = 5; i >= 0; i = i - 0.1) {
            CAP.add(String.format("%.1f", i));
        }
    }
}
