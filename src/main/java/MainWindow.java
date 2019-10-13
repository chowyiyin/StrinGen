import java.io.UnsupportedEncodingException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainWindow extends AnchorPane {
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

    public void setGen(Generator gen) {
        generator = gen;
    }

    @FXML
    private void handleUserInput() throws UnsupportedEncodingException {
        String moduleCode = userInput.getText().toUpperCase();
        boolean isNotEmptyUserInput = !moduleCode.equals("");
        if (isNotEmptyUserInput) {
            Module module = new Module(moduleCode);
            EntryWindow entryWindow = new EntryWindow(module, generator, this);
            borderPane.setCenter(entryWindow);
            userInput.clear();
        }
    }
}