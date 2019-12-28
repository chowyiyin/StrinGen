package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import stringen.Util;
import stringen.logic.requirements.ModulePrerequisite;
import stringen.ui.exceptions.InvalidInputException;

public class ModulePrerequisiteCard extends RequirementCard {

    @FXML
    private ComboBox<String> gradeDropdown;

    @FXML
    private TextField moduleCodeField;

    @FXML
    private Button orButton;

    private EntryFieldCard parent;
    private Label orLabel;

    public ModulePrerequisiteCard(EntryFieldCard parent) {
        try {
            this.parent = parent;
            FXMLLoader fxmlLoader = new FXMLLoader(ModulePrerequisiteCard.class
                    .getResource("/view/ModulePrerequisiteCard.fxml"));
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

    @FXML
    public void addNewEntry() {
        changeOrButtonToLabel();
        parent.addNewCard(new ModulePrerequisiteCard(parent));
    }

    public ModulePrerequisite getModulePrerequisite() throws InvalidInputException {
        String moduleCode = moduleCodeField.getText().toUpperCase().trim();
        if (moduleCode.isEmpty()) {
            throw new InvalidInputException("Please enter a module code for the Module Prerequisite field");
        }
        String grade = gradeDropdown.getValue() == null ? ModulePrerequisite.DEFAULT_GRADE : gradeDropdown.getValue();
        return new ModulePrerequisite(moduleCode, grade);
    }

}
