package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import stringen.Util;
import stringen.logic.requirements.ModulePreclusion;
import stringen.ui.exceptions.InvalidInputException;

/**
 * Represents a UI component that collects information about a {@code ModulePreclusion}.
 */
public class ModulePreclusionCard extends RequirementCard {

    @FXML
    private ComboBox<String> gradeDropdown;

    @FXML
    private TextField moduleCodeField;

    @FXML
    private Button orButton;

    private EntryFieldCard parent;
    private Label orLabel;

    public ModulePreclusionCard(EntryFieldCard parent) {
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

    /**
     * Adds a new entry (horizontally on the UI).
     * Implies a logical or relationship between the current entry and the newly added entry.
     */
    @FXML
    public void addNewEntry() {
        changeOrButtonToLabel();
        parent.addNewCard(new ModulePreclusionCard(parent));
    }

    /**
     * Extracts the user-inputted information and returns a {@code ModulePreclusion}.
     * @return The corresponding {@code ModulePreclusion}.
     * @throws InvalidInputException If any field is empty.
     */
    public ModulePreclusion getModulePreclusion() throws InvalidInputException {
        String moduleCode = moduleCodeField.getText().toUpperCase().trim();
        if (moduleCode.isEmpty()) {
            throw new InvalidInputException("Please enter a module code for the Module Preclusion field");
        }
        String grade = gradeDropdown.getValue() == null ? ModulePreclusion.DEFAULT_GRADE : gradeDropdown.getValue();
        return new ModulePreclusion(moduleCode, grade);
    }

}