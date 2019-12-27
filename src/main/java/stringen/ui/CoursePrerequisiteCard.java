package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import stringen.logic.requirements.CoursePrerequisite;

public class CoursePrerequisiteCard extends RequirementCard {

    @FXML
    private TextField courseCodeField;

    @FXML
    private Button orButton;

    private EntryFieldCard parent;
    private Label orLabel;

    public CoursePrerequisiteCard(EntryFieldCard parent) {
        try {
            this.parent = parent;
            FXMLLoader fxmlLoader = new FXMLLoader(CoursePrerequisiteCard.class
                    .getResource("/view/CoursePrerequisiteCard.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addNewEntry() {
        changeOrButtonToLabel();
        parent.addNewCard(new CoursePrerequisiteCard(parent));
    }

    public CoursePrerequisite getCoursePrerequisite() {
        return new CoursePrerequisite(courseCodeField.getText());
    }

}
