package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import stringen.logic.requirements.ALevelPrerequisite;
import stringen.ui.exceptions.InvalidInputException;

public class ALevelPrerequisiteCard extends RequirementCard {

    @FXML
    private TextField subjectField;

    @FXML
    private TextField gradeField;

    @FXML
    private Button orButton;

    private EntryFieldCard parent;
    private Label orLabel;

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
    public void addNewEntry() {
        changeOrButtonToLabel();
        parent.addNewCard(new ALevelPrerequisiteCard(parent));
    }

    public ALevelPrerequisite getALevelPrerequisite() throws InvalidInputException {
        String subjectCode = subjectField.getText().toUpperCase().trim();
        String grade = gradeField.getText().toUpperCase().trim();
        if (subjectCode.isEmpty()) {
            throw new InvalidInputException("Please enter a subject code for the A-Level Prerequisite field");
        }
        if (grade.isEmpty()) {
            throw new InvalidInputException("Please enter a grade for the A-Level Prerequisite field");
        }
        return new ALevelPrerequisite(subjectCode, grade);
    }

}
