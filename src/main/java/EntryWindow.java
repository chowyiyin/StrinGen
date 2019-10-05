import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class EntryWindow extends AnchorPane {

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Label header;

    String moduleCode;
    Generator generator;

    public EntryWindow(String moduleCode, Generator generator, MainWindow mainWindow) {
        this.moduleCode = moduleCode.toUpperCase();
        this.generator = generator;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/EntryWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
       // header.setText(moduleCode);
        /*
        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll("2011", "2012", "2013");
        comboBox.getSelectionModel().select("2011");
         */
    }

    @FXML
    public void initialize() {
        header.setText(moduleCode);
        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll("2011", "2012", "2013");
        comboBox.getSelectionModel().select("2011");
    }

}
