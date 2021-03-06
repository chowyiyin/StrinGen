package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import stringen.logic.requirements.ModuleConcurrent;
import stringen.ui.exceptions.InvalidInputException;

/**
 * Represents a UI component that collects information about a {@code ModuleConcurrentCard}.
 */
public class ModuleConcurrentCard extends RequirementCard {

    @FXML
    private TextField moduleCodeField;

    @FXML
    private Button orButton;

    private EntryFieldCard parent;
    private Label orLabel;

    public ModuleConcurrentCard(EntryFieldCard parent) {
        try {
            this.parent = parent;
            FXMLLoader fxmlLoader = new FXMLLoader(ModuleConcurrentCard.class
                    .getResource("/view/ModuleConcurrentCard.fxml"));
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
        parent.addNewCard(new ModuleConcurrentCard(parent));
    }

    /**
     * Extracts the user-inputted information and returns a {@code ModuleConcurrent}.
     * @return The corresponding {@code ModuleConcurrent}.
     * @throws InvalidInputException If the module code field is empty.
     */
    public ModuleConcurrent getModuleConcurrent() throws InvalidInputException {
        String moduleCode = moduleCodeField.getText().toUpperCase().trim();
        if (moduleCode.isEmpty()) {
            throw new InvalidInputException("Please enter a module code for the Concurrent Module field");
        }
        return new ModuleConcurrent(moduleCode);
    }
}
