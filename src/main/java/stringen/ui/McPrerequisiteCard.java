package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import stringen.logic.requirements.McPrerequisite;
import stringen.logic.requirements.ModulePrerequisite;
import stringen.ui.exceptions.InvalidInputException;

/**
 * Represents a UI component that collects information about a {@code McPrerequisite}.
 */
public class McPrerequisiteCard extends RequirementCard {

    @FXML
    private TextField mcCountField;

    @FXML
    private TextField moduleCodeField;

    @FXML
    private Button orButton;

    private EntryFieldCard parent;
    private Label orLabel;

    public McPrerequisiteCard(EntryFieldCard parent) {
        try {
            this.parent = parent;
            FXMLLoader fxmlLoader = new FXMLLoader(McPrerequisiteCard.class
                    .getResource("/view/McPrerequisiteCard.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts the user-inputted information and returns a {@code McPrerequisite} if no module code is given.
     * @return The corresponding {@code McPrerequisite}.
     */
    public McPrerequisite getMcPrerequisite() {
        return new McPrerequisite(mcCountField.getText().toUpperCase().trim());
    }

    /**
     * Calculates the number of modules that correspond to the given MC count.
     * @return The number of modules
     * @throws InvalidInputException If no MC count is given or the MC count given cannot be parsed into an
     * {@code Integer}.
     */
    public int getNumberOfModules() throws InvalidInputException {
        try {
            return Integer.parseInt(mcCountField.getText().trim()) / 4;
        } catch (NumberFormatException exception) {
            if (mcCountField.getText().isEmpty()) {
                throw new InvalidInputException("Please enter a MC count for the MC Prerequisite field");
            } else {
                throw new InvalidInputException("Please enter an integer MC count for the MC Prerequisite field");
            }
        }
    }

    /**
     * Extracts the user-inputted information and returns a {@code ModulePrerequisite} if a module code is given.
     * @return The corresponding {@code ModulePrerequisite}.
     */
    public ModulePrerequisite getModule() {
        return new ModulePrerequisite(moduleCodeField.getText().toUpperCase(), ModulePrerequisite.DEFAULT_GRADE);
    }

    /**
     * Checks if the module code field is empty.
     * @return True if it is not empty.
     */
    public boolean hasModuleCode() {
        return !moduleCodeField.getText().trim().equals("");
    }

    /**
     * Adds a new entry (horizontally on the UI).
     * Implies a logical or relationship between the current entry and the newly added entry.
     */
    @FXML
    public void addNewEntry() {
        changeOrButtonToLabel();
        McPrerequisiteCard newCard = new McPrerequisiteCard(parent);
        newCard.removeMcCountField();
        parent.addNewCard(newCard);
    }

    /**
     * Removes the MC count field from the card.
     */
    public void removeMcCountField() {
        getChildren().remove(mcCountField);
    }

}
