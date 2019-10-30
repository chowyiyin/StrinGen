package stringen.ui;

import stringen.logic.Module;
import stringen.logic.StringGenerator;

public class Generator {

    public String generateString(Module module) {
        //TODO IMPLEMENT
        return new StringGenerator(module).generateString();
    }

    public String generateDetails(Module module) {
        //TODO IMPLEMENT
        return "This will be the generated details";
    }

}
