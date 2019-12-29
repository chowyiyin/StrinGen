package stringen.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Represents a UI component that collects information about requirements, each with a different {@code EntryType}.
 */
public class RequirementCard extends HBox {

    @FXML
    private Button orButton;

    private Label orLabel;

    private EntryFieldCard parent;

    /**
     * Changes the horizontal `add Or` button to an `or` label.
     */
    protected void changeOrButtonToLabel() {
        getChildren().remove(orButton);
        orLabel = new Label("OR");
        orLabel.getStyleClass().add("label");
        getChildren().add(orLabel);
    }

    /**
     * Changes the horizontal `or` label to the horizontal `add Or` button.
     */
    public void changeOrLabelToButton() {
        getChildren().remove(orLabel);
        getChildren().add(orButton);
    }

}
