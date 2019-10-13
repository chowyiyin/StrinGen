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

    @FXML
    private Label detailsLabel;

    private Generator generator;
    private Module module;

    private String generatedString;
    private String generatedDetails;

    public ResultWindow(Generator generator, Module module) {
        this.generator = generator;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CommandBox.class.getResource("/view/ResultWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        generatedString = generator.generateString(module);
        generatedDetails = generator.generateDetails(module);
        stringGenerated.setText(generatedString);
        detailsLabel.setText(generatedDetails);
    }

    @FXML
    public void copyToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(generatedString);
        clipboard.setContent(content);
    }

}
