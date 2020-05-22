package stringen.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import stringen.Util;
import stringen.logic.requirements.ALevelPrerequisite;
import stringen.ui.exceptions.InvalidInputException;

/**
 * Represents a UI component that collects information about a {@code ALevelPrerequisite}.
 */
public class ALevelPrerequisiteCard extends RequirementCard {

    @FXML
    private ComboBox<String> subjectField;

    @FXML
    private TextField gradeField;

    @FXML
    private Button orButton;

    private EntryFieldCard parent;
    private Label orLabel;

    private HashMap<String, ArrayList<String>> subjectNameToCodeMapping;

    public ALevelPrerequisiteCard(EntryFieldCard parent) {
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
        subjectNameToCodeMapping = Util.ALEVELSUBJECTS;
        Set<String> subjectNamesSet = subjectNameToCodeMapping.keySet();
        List<String> subjectNames = Arrays.asList(subjectNamesSet.toArray(new String[subjectNamesSet.size()]));
        Collections.sort(subjectNames);
        subjectField.getItems().addAll(subjectNames);
    }

    /**
     * Adds a new entry (horizontally on the UI).
     * Implies a logical or relationship between the current entry and the newly added entry.
     */
    @FXML
    public void addNewEntry() {
        changeOrButtonToLabel();
        parent.addNewCard(new ALevelPrerequisiteCard(parent));
    }

    /**
     * Extracts the user-inputted information and returns a {@code ALevelPrerequisite}.
     * @return The corresponding {@code ALevelPrerequisite}.
     * @throws InvalidInputException If any field is empty.
     */
    public ArrayList<ALevelPrerequisite> getALevelPrerequisite() throws InvalidInputException {
        String subjectName = subjectField.getValue();
        ArrayList<String> subjectCodes = subjectNameToCodeMapping.get(subjectName);
        String grade = gradeField.getText().toUpperCase().trim();
        if (grade.isEmpty()) {
            throw new InvalidInputException("Please enter a grade for the A-Level Prerequisite field");
        }

        ArrayList<ALevelPrerequisite> prerequisites = new ArrayList<>();
        for (String subjectCode: subjectCodes) {
            prerequisites.add(new ALevelPrerequisite(subjectCode, grade));
        }
        return prerequisites;
    }

}
