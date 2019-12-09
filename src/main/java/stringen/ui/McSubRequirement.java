package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class McSubRequirement extends HBox {

    @FXML
    private ComboBox<String> modulePrefix;

    @FXML
    private TextField credits;

    public McSubRequirement() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(McSubRequirement.class.getResource("/view/McSubRequirement.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        modulePrefix.getItems().addAll("HY", "SC");
        modulePrefix.getSelectionModel().select("MODULE PREFIX");
    }

    public String getMcs(Module module) {
        return credits.getText();
    }
}


