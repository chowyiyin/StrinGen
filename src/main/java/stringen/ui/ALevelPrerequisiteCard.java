package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import stringen.ALevelSubject;
import stringen.Util;
import stringen.logic.requirements.ALevelPrerequisite;

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
        subjectDropdown.getItems().addAll(Util.A_LEVEL_SUBJECTS);
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
        String subjectName = subjectDropdown.getValue();
        ALevelSubject subject = ALevelSubject.getSubject(subjectName);
        String subjectCode = subject.getSubjectCode();
        return new ALevelPrerequisite(subjectCode, gradeDropdown.getValue());
    }

}
