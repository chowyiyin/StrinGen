package stringen.logic;

import java.util.ArrayList;
import java.util.Arrays;

public class StringGenerator {

    public static String appendOrGroups(ArrayList<OrGroup> orGroups) {
        return append(orGroups, LogicManager.OPERATOR_AND);
    }

    public static String appendAndGroups(ArrayList<AndGroup> andGroups) {
        return append(andGroups, LogicManager.OPERATOR_OR);
    }

    public static String or(String... strings) {
        return appendStrings(strings, LogicManager.OPERATOR_AND);
    }

    private static String append(ArrayList<? extends Group> groups, String operator) {
        StringBuilder string = new StringBuilder();
        groups.stream().forEach(group -> {
            if (string.length() == 0) {
                string.append(group.generateString());
            } else {
                string.append(operator + group.generateString());
            }
        });
        return string.toString();
    }

    private static String appendStrings(String[] strings, String operator) {
        StringBuilder string = new StringBuilder();
        Arrays.stream(strings).forEach(str -> {
            if (string.length() == 0) {
                string.append(str);
            } else {
                string.append(operator + str);
            }
        });
        return string.toString();
    }
}
