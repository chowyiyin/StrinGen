package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import stringen.logic.requirements.MajorPrerequisite;
import stringen.ui.exceptions.InvalidInputException;

/**
 * Represents a UI component that collects information about a {@code MajorPrerequisite}.
 */
public class MajorPrerequisiteCard extends RequirementCard {

    @FXML
    private TextField majorCodeField;

    @FXML
    private Button orButton;

    private EntryFieldCard parent;
    private Label orLabel;

    public MajorPrerequisiteCard(EntryFieldCard parent) {
        try {
            this.parent = parent;
            FXMLLoader fxmlLoader = new FXMLLoader(MajorPrerequisiteCard.class
                    .getResource("/view/MajorPrerequisiteCard.fxml"));
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
        parent.addNewCard(new MajorPrerequisiteCard(parent));
    }

    /**
     * Extracts the user-inputted information and returns a {@code MajorPrerequisite}.
     * @return The corresponding {@code MajorPrerequisite}.
     * @throws InvalidInputException If any field is empty.
     */
    public MajorPrerequisite getMajorPrerequisite() throws InvalidInputException {
        String majorCode = majorCodeField.getText().toUpperCase().trim();
        if (majorCode.isEmpty()) {
            throw new InvalidInputException("Please enter a major code for the Major Prerequisite field");
        }
        return new MajorPrerequisite(majorCode);
    }

}
