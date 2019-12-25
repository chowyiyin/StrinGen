package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import stringen.Util;
import stringen.logic.requirements.CapPrerequisite;

public class CapPrerequisiteCard extends HBox {

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

    public CapPrerequisite getCapPrerequisite() {
        return new CapPrerequisite(capDropdown.getValue());
    }

}
