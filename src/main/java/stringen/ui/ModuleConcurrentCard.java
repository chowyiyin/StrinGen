package stringen.ui;

import java.io.IOException;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import stringen.logic.requirements.ModuleConcurrent;

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

    @FXML
    public void addNewEntry() {
        changeOrButtonToLabel();
        parent.addNewCard(new ModuleConcurrentCard(parent));
    }

    public ModuleConcurrent getModuleConcurrent() {
        return new ModuleConcurrent(moduleCodeField.getText());
    }
}
