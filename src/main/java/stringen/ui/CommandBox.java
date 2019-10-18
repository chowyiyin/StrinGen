package stringen.ui;

import java.io.UnsupportedEncodingException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import stringen.exceptions.ParseModuleException;
import stringen.logic.Module;
import stringen.logic.ParseModuleAlert;
import stringen.logic.Parser;

public class CommandBox extends AnchorPane {
    @FXML
    private AnchorPane enterField;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private BorderPane borderPane;

    private Generator generator;

    private boolean warningWasThrown = false;

    public void setGen(Generator gen) {
        generator = gen;
    }

    @FXML
    private void handleUserInput() throws UnsupportedEncodingException {
        String input = userInput.getText();
        try {
            Module module;
            if (input.length() != 0 && !warningWasThrown) {
                module = Parser.parseModule(input);

            } else {
                module = Parser.parseModuleOverridden(input);
                warningWasThrown = false;
            }
            EntryWindow entryWindow = new EntryWindow(module, generator, this);
            entryWindow.maxHeightProperty().bind(borderPane.heightProperty());
            borderPane.setCenter(entryWindow);
            userInput.clear();
        } catch (ParseModuleException e) {
            ParseModuleAlert a = new ParseModuleAlert(e.getMessage(), Module.DATA_TYPE);
            warningWasThrown = true;
        }

    }

    void changeScreen(AnchorPane newScreen) {
        newScreen.maxHeightProperty().bind(borderPane.heightProperty());
        borderPane.setCenter(newScreen);
    }

}