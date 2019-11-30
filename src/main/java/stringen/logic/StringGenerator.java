package stringen.logic;

import java.util.ArrayList;

import stringen.logic.prerequisites.Prerequisite;

public class StringGenerator {

    public static String generateStringForCohorts(ArrayList<Group> groups) {
        StringBuilder string = new StringBuilder();
        groups.stream()
                .forEach(group -> {if (string.length() != 0) { string.append(Logic.OPERATOR_AND);}
                                                string.append(group.generateString());});
        return string.toString();
    }

    public static String generateStringForGroups(Group group) {
        StringBuilder string = new StringBuilder();
        ArrayList<Prerequisite> lonePrerequisites = group.getLonePrerequisites();
        ArrayList<Group> groupsInGroup = group.getGroups();

        boolean isEmptyString = string.length() == 0;
        lonePrerequisites.stream().forEach(prerequisite -> {
            if (!isEmptyString) {
                string.append(prerequisite.generateString() + Logic.OPERATOR_OR);
            } else {
                string.append(prerequisite.generateString());
            }
        });

        groupsInGroup.stream().forEach(grp -> {
            if (!isEmptyString) {
                string.append(grp.generateString() + Logic.OPERATOR_OR);
            } else {
                string.append(grp.generateString());
            }
        });

        return string.toString();
    }

    public static String combine(Cohort firstCohort, Cohort secondCohort) {
        return appendBrackets(firstCohort.generateString() + Logic.OPERATOR_OR + secondCohort.generateString());
    }

    public static String appendBrackets(String value) {
        return "(" + value + ")";
    }

}
