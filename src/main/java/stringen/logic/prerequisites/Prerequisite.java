package stringen.logic.prerequisites;

public interface Prerequisite {

    String generateString();

    default String appendBrackets(String value) {
        return "(" + value + ")";
    }

}
