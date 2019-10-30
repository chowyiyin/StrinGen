package stringen.logic;


import java.util.ArrayList;

public class StringGenerator {

    private Module module;

    public StringGenerator(Module module) {
        this.module = module;
    }

    public String generateString() {
        StringBuilder string = new StringBuilder();
        ArrayList<Cohort> cohorts = module.getCohorts();
        string.append(cohorts.get(0).toString());
        for (int i = 1; i < cohorts.size(); i++) {
            string.append("&" + cohorts.get(i).toString());
        }
        return string.toString();
    }

    public static String appendBrackets(String value) {
        return "(" + value + ")";
    }

}
