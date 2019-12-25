package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import stringen.logic.prerequisites.McPrerequisite;

public class McPrerequisiteCard extends HBox {

    @FXML
    private TextField mcCountField;

    @FXML
    private TextField modulePrefixField;

    public McPrerequisiteCard() {
        try {
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
        return new McPrerequisite(mcCountField.getText(), modulePrefixField.getText());
    }

}
