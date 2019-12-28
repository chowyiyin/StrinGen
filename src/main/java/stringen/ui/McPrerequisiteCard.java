package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import stringen.logic.requirements.McPrerequisite;
import stringen.logic.requirements.ModulePrerequisite;

public class McPrerequisiteCard extends RequirementCard {

    @FXML
    private TextField mcCountField;

    @FXML
    private TextField modulePrefixField;

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

    public McPrerequisite getMcPrerequisite() {
        return new McPrerequisite(mcCountField.getText());
    }

    public int getNumberOfModules() {
        return Integer.parseInt(mcCountField.getText().trim()) / 4;
    }

    public ModulePrerequisite getModule() {
        return new ModulePrerequisite(modulePrefixField.getText(), ModulePrerequisite.DEFAULT_GRADE);
    }

    public boolean hasPrefix() {
        return !modulePrefixField.getText().trim().equals("");
    }

    @FXML
    public void addNewEntry() {
        changeOrButtonToLabel();
        McPrerequisiteCard newCard = new McPrerequisiteCard(parent);
        newCard.removeMcCountField();
        parent.addNewCard(newCard);
    }

    public void removeMcCountField() {
        getChildren().remove(mcCountField);
    }

}
