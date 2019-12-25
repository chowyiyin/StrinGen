package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;

public class ResultWindow extends AnchorPane {

    @FXML
    private Label stringGenerated;

    private String generatedString;

    public ResultWindow(String generatedString) {
        this.generatedString = generatedString;
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
    }

    @FXML
    public void copyToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(generatedString);
        clipboard.setContent(content);
    }

}
