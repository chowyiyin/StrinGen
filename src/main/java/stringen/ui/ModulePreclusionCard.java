package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import stringen.Util;
import stringen.logic.requirements.ModulePreclusion;

public class ModulePreclusionCard extends HBox {

    @FXML
    private ComboBox<String> gradeDropdown;

    @FXML
    private TextField moduleCodeField;

    private HBox parent;

    public ModulePreclusionCard(HBox parent) {
        try {
            this.parent = parent;
            FXMLLoader fxmlLoader = new FXMLLoader(ModulePreclusionCard.class
                    .getResource("/view/ModulePreclusionCard.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        gradeDropdown.getItems().addAll(Util.GRADES);
    }

    @FXML
    public void addNewEntry() {
        getChildren().remove(getChildren().size() - 1);
        Label label = new Label("OR");
        label.getStyleClass().add("label");
        getChildren().add(label);
        parent.getChildren().add(new ModulePreclusionCard(parent));
    }

    public ModulePreclusion getModulePreclusion() {
        String moduleCode = moduleCodeField.getText();
        String grade = gradeDropdown.getValue() == null ? ModulePreclusion.DEFAULT_GRADE : gradeDropdown.getValue();
        return new ModulePreclusion(moduleCode, grade);
    }

}