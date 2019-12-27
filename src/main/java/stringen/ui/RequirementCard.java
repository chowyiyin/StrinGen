package stringen.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public abstract class RequirementCard extends HBox {

    @FXML
    private Button orButton;

    private Label orLabel;

    private EntryFieldCard parent;

    public void addOrButton() {
        getChildren().add(orButton);
    }

    protected void changeOrButtonToLabel() {
        getChildren().remove(orButton);
        orLabel = new Label("OR");
        orLabel.getStyleClass().add("label");
        getChildren().add(orLabel);
    }

    public void changeOrLabelToButton() {
        getChildren().remove(orLabel);
        getChildren().add(orButton);
    }

}
