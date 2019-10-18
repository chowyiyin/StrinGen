package stringen.logic;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class ParseModuleAlert {
    public static final String OVERRIDE_INSTRUCTIONS = "\nTo continue, press enter again.";

    public ParseModuleAlert(String message, String type) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(type);
        a.setHeaderText(message);
        Label content = new Label(Module.USAGE_MESSAGE + OVERRIDE_INSTRUCTIONS);
        content.setWrapText(true);
        a.getDialogPane().setContent(content);
        a.show();
    }
}
