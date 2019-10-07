import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class EntryWindow extends AnchorPane {

    @FXML
    private ComboBox<String> cohortStart;

    @FXML
    private Label header;

    private Module module;
    private Generator generator;

    public EntryWindow(Module module, Generator generator, MainWindow mainWindow) {
        this.module = module;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/EntryWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        header.setText("Module Code : " + module.getModuleCode());
        cohortStart.getItems().addAll("2011", "2012", "2013");
        cohortStart.getSelectionModel().select("YEAR");
    }

}
