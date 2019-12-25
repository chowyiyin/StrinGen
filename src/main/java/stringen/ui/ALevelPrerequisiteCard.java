package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import stringen.Util;
import stringen.logic.prerequisites.ALevelPrerequisite;

public class ALevelPrerequisiteCard extends HBox {

    @FXML
    private ComboBox<String> subjectDropdown;

    @FXML
    private ComboBox<String> gradeDropdown;

    private HBox parent;

    public ALevelPrerequisiteCard(HBox parent) {
        try {
            this.parent = parent;
            FXMLLoader fxmlLoader = new FXMLLoader(ALevelPrerequisiteCard.class
                    .getResource("/view/ALevelPrerequisiteCard.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        //add subjects to dropdown
        gradeDropdown.getItems().addAll(Util.GRADES);
    }

    @FXML
    public void addNewEntry() {
        getChildren().remove(getChildren().size() - 1);
        Label label = new Label("OR");
        label.getStyleClass().add("label");
        getChildren().add(label);
        parent.getChildren().add(new ALevelPrerequisiteCard(parent));
    }

    public ALevelPrerequisite getALevelPrerequisite() {
        return new ALevelPrerequisite(subjectDropdown.getValue(), gradeDropdown.getValue());
    }

}
