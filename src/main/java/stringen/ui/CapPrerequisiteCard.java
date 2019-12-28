package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import stringen.Util;
import stringen.logic.requirements.CapPrerequisite;
import stringen.ui.exceptions.InvalidInputException;

public class CapPrerequisiteCard extends RequirementCard {

    @FXML
    private ComboBox<String> capDropdown;

    public CapPrerequisiteCard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CapPrerequisiteCard.class
                    .getResource("/view/CapPrerequisiteCard.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        capDropdown.getItems().addAll(Util.CAP);
    }

    public CapPrerequisite getCapPrerequisite() throws InvalidInputException {
        String cap = capDropdown.getValue();
        if (cap == null) {
            throw new InvalidInputException("Please choose a CAP value for the CAP Prerequisite field");
        }
        return new CapPrerequisite(cap);
    }

}
