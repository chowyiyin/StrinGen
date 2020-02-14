package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import stringen.logic.requirements.CoursePrerequisite;
import stringen.ui.exceptions.InvalidInputException;

/**
 * Represents a UI component that collects information about a {@code CoursePrerequisite}.
 */
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

    /**
     * Adds a new entry (horizontally on the UI).
     * Implies a logical or relationship between the current entry and the newly added entry.
     */
    @FXML
    public void addNewEntry() {
        changeOrButtonToLabel();
        parent.addNewCard(new CoursePrerequisiteCard(parent));
    }

    /**
     * Extracts the user-inputted information and returns a {@code CoursePrerequisite}.
     * @return The corresponding {@code CoursePrerequisite}.
     * @throws InvalidInputException If any field is empty.
     */
    public CoursePrerequisite getCoursePrerequisite() throws InvalidInputException{
        String courseCode = courseCodeField.getText().toUpperCase().trim();
        if (courseCode.isEmpty()) {
            throw new InvalidInputException("Please enter a course code for the Course Prerequisite field");
        }
        return new CoursePrerequisite(courseCode);
    }

}
