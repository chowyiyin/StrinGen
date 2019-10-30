package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import stringen.Util;

public class ModuleRequirement extends HBox {

    public static String defaultGrade = "D";

    @FXML
    private TextField textBox;

    @FXML
    private ComboBox minimumGrade;


    public ModuleRequirement() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CommandBox.class.
                    getResource("/view/ModulePrerequisite.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        this.minimumGrade.getItems().addAll(Util.GRADES);
    }

    public String getCode() {
        return textBox.getText();
    }

    public String getMinimumGrade() {
        try {
            return minimumGrade.getSelectionModel().getSelectedItem().toString();
        } catch (NullPointerException e) {
            return defaultGrade;
        }
    }

}


