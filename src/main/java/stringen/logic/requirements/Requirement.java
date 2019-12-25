package stringen.logic.requirements;

public interface Requirement {

    String generateString();

    default String appendSquareBrackets(String value) {
        return "[" + value + "]";
    }
}
