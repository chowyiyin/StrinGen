package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import stringen.logic.requirements.CoursePreclusion;
import stringen.ui.exceptions.InvalidInputException;

/**
 * Represents a UI component that collects information about a {@code CoursePreclusion}.
 */
public class CoursePreclusionCard extends RequirementCard {

    @FXML
    private TextField courseCodeField;

    @FXML
    private Button orButton;

    private EntryFieldCard parent;
    private Label orLabel;

    public CoursePreclusionCard(EntryFieldCard parent) {
        try {
            this.parent = parent;
            FXMLLoader fxmlLoader = new FXMLLoader(CoursePreclusionCard.class
                    .getResource("/view/CoursePreclusionCard.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new entry (horizontally on the UI).
     * Implies a logical or relationship between the current entry and the newly added entry.
     */
    @FXML
    public void addNewEntry() {
        changeOrButtonToLabel();
        parent.addNewCard(new CoursePreclusionCard(parent));
    }

    /**
     * Extracts the user-inputted information and returns a {@code CoursePreclusion}.
     * @return The corresponding {@code CoursePreclusion}.
     * @throws InvalidInputException If the course code field is empty.
     */
    public CoursePreclusion getCoursePreclusion() throws InvalidInputException {
        String courseCode = courseCodeField.getText().toUpperCase().trim();
        if (courseCode.isEmpty()) {
            throw new InvalidInputException("Please enter a course code for the Course Preclusion field");
        }
        return new CoursePreclusion(courseCode);
    }

}
