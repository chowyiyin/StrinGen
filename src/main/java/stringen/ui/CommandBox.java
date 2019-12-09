package stringen.ui;

import java.io.UnsupportedEncodingException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import stringen.exceptions.ParseModuleException;

public class CommandBox extends GridPane {
    @FXML
    private AnchorPane enterField;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private StackPane stackPane;

    private Generator generator;

    private Parser parser;

    private boolean warningWasThrown = false;

    public void setParser(Parser parser) {
        this.parser = parser;
    }

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
            EntryWindow entryWindow = new EntryWindow(module, generator, this, parser);
            stackPane.getChildren().add(entryWindow);
            entryWindow.minHeightProperty().bind(stackPane.prefHeightProperty());
            entryWindow.minWidthProperty().bind(stackPane.prefWidthProperty());
            userInput.clear();
        } catch (ParseModuleException e) {
            ParseModuleAlert a = new ParseModuleAlert(e.getMessage(), Module.DATA_TYPE);
            warningWasThrown = true;
        }

    }

    void changeScreen(AnchorPane newScreen) {
        newScreen.maxHeightProperty().bind(stackPane.heightProperty());
        stackPane.getChildren().add(newScreen);
    }

}