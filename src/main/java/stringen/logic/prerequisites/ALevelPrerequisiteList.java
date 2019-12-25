package stringen.logic.prerequisites;

import java.util.ArrayList;

import stringen.logic.Cohort;
import stringen.logic.StringGenerator;

public class ALevelPrerequisiteList implements Prerequisite {
    public static String PREFIX = "ASUB_PR";

    private ArrayList<ALevelPrerequisite> aLevelPrerequisites = new ArrayList<>();
    private ArrayList<Cohort> cohorts = new ArrayList<>();

    public ALevelPrerequisiteList(ArrayList<ALevelPrerequisite> aLevelPrerequisites) {
        this.aLevelPrerequisites.addAll(aLevelPrerequisites);
    }

    public ALevelPrerequisiteList() {
    }

    @Override
    public String generateString() {
        StringBuilder string = new StringBuilder();
        //TODO: implement the number
        string.append("1");
        aLevelPrerequisites.stream().forEach(sub -> string.append("," + sub.generateString()));
        return PREFIX + StringGenerator.appendBrackets(string.toString());
    }

    public boolean isEmpty() {
        return aLevelPrerequisites.size() == 0;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof ALevelPrerequisiteList) {
                ALevelPrerequisiteList other = (ALevelPrerequisiteList) o;
                return aLevelPrerequisites.equals(other.aLevelPrerequisites);
            } else {
                return false;
            }
        }
    }

}
