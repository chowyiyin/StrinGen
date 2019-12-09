package stringen.logic;

import java.util.ArrayList;

public class AndGroup extends Group {
    private ArrayList<OrGroup> orGroups = new ArrayList<>();

    public AndGroup() {
    }

    public void addOrGroup(OrGroup orGroup) {
        orGroups.add(orGroup);
    }

    public String generateString() {
        return StringGenerator.appendOrGroups(orGroups);
    }
}
