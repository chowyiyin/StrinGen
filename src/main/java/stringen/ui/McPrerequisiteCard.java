package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import stringen.logic.requirements.McPrerequisite;

public class McPrerequisiteCard extends HBox {

    @FXML
    private TextField mcCountField;

    @FXML
    private TextField modulePrefixField;

    private HBox parent;

    public McPrerequisiteCard(HBox parent) {
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
        return new McPrerequisite(mcCountField.getText(), modulePrefixField.getText());
    }

    @FXML
    public void addNewEntry() {
        getChildren().remove(getChildren().size() - 1);
        Label label = new Label("OR");
        label.getStyleClass().add("label");
        getChildren().add(label);
        McPrerequisiteCard newCard = new McPrerequisiteCard(parent);
        newCard.removeMcCountField();
        parent.getChildren().add(newCard);
    }

    public void removeMcCountField() {
        getChildren().remove(mcCountField);
    }

}
