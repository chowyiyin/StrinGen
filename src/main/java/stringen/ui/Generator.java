package stringen.ui;

import stringen.logic.Logic;
import stringen.logic.Module;

public class Generator {

    public String generateString(Module module) {
        //TODO IMPLEMENT
        return Logic.generateString(module.getCohorts());
    }

    public String generateDetails(Module module) {
        //TODO IMPLEMENT
        return "This will be the generated details";
    }

}
