package stringen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import stringen.logic.Cohort;
import stringen.logic.ExcelMappingReader;


public class Util {
    private static final Logger logger = Logger.getLogger(Util.class.getName());

    public static final ArrayList<String> YEARS = new ArrayList<>();
    public static final ArrayList<String> GRADES = new ArrayList<>();
    public static final ArrayList<String> CAP = new ArrayList<>();
    // stores the mapping between the A level subject names to their subject codes
    public static final HashMap<String, ArrayList<String>> ALEVELSUBJECTS = new HashMap<>();

    public static void initialise() {
        initialiseYears();
        initialiseGrades();
        initialiseCap();
        initialiseALevelSubjects();
    }

    public static void initialiseYears() {
        YEARS.add(Cohort.DEFAULT_DASH);
        for (int i = 2013; i <= 2050; i++) {
            YEARS.add("" + i);
        }
    }

    public static void initialiseGrades() {
        GRADES.add("A+");
        GRADES.add("A");
        GRADES.add("A-");
        GRADES.add("B+");
        GRADES.add("B");
        GRADES.add("B-");
        GRADES.add("C+");
        GRADES.add("C");
        GRADES.add("D+");
        GRADES.add("D");
        GRADES.add("F");
    }

    public static void initialiseCap() {
        for (double i = 5; i >= 0; i = i - 0.05) {
            CAP.add(String.format("%.2f", i));
        }
    }

    public static void initialiseALevelSubjects() {
        try {
            InputStream inputStream = Util.class.getResourceAsStream("/excel/AOLevelModules.xlsx");
            ALEVELSUBJECTS.putAll(ExcelMappingReader.readFile(inputStream));
        } catch (IOException | InvalidFormatException exception) {
            logger.setLevel(Level.WARNING);
            logger.warning("Failed to load AO subjects names : " + exception);
        }
    }

}
