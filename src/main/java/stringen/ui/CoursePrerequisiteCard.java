package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import stringen.logic.prerequisites.CoursePrerequisite;

public class CoursePrerequisiteCard extends HBox {

    @FXML
    private TextField courseCodeField;

    private HBox parent;

    public CoursePrerequisiteCard(HBox parent) {
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
        getChildren().remove(getChildren().size() - 1);
        Label label = new Label("OR");
        label.getStyleClass().add("label");
        getChildren().add(label);
        parent.getChildren().add(new CoursePrerequisiteCard(parent));
    }

    public CoursePrerequisite getCoursePrerequisite() {
        return new CoursePrerequisite(courseCodeField.getText());
    }

}
