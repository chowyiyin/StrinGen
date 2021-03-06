package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * Represents a UI component that shows the resulting generated string obtained from the user's input.
 */
public class ResultWindow extends ScrollPane {

    @FXML
    private Label stringGenerated;

    @FXML
    private Label lengthWarningLabel;

    @FXML
    private Button editFieldsButton;

    private String generatedString;
    private MainWindow mainWindow;

    public ResultWindow(String generatedString, MainWindow mainWindow) {
        this.generatedString = generatedString;
        this.mainWindow = mainWindow;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ResultWindow.class.getResource("/view/ResultWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        stringGenerated.setText(generatedString);
        if (stringGenerated.getText().length() > 5000) {
            lengthWarningLabel.setId("warningLabel");
            lengthWarningLabel.setText("The generated string is above 5000 characters");
        } else {
            lengthWarningLabel.setText(null);
        }
    }

    /**
     * Copies the string to the user's system clipboard.
     */
    @FXML
    public void copyToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(generatedString);
        clipboard.setContent(content);
    }

    /**
     * Refreshes the {@code MainWindow} to show a new {@code EntryWindow}.
     */
    @FXML
    public void generateAnotherString() {
        mainWindow.refresh();
    }

    @FXML
    public void editFields() {
        mainWindow.returnToEntryWindows();
    }

}
