package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import stringen.logic.requirements.ALevelPrerequisite;

public class ALevelPrerequisiteCard extends HBox {

    @FXML
    private TextField subjectField;

    @FXML
    private TextField gradeField;

    private HBox parent;

    public ALevelPrerequisiteCard(HBox parent) {
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
        getChildren().remove(getChildren().size() - 1);
        Label label = new Label("OR");
        label.getStyleClass().add("label");
        getChildren().add(label);
        parent.getChildren().add(new ALevelPrerequisiteCard(parent));
    }

    public ALevelPrerequisite getALevelPrerequisite() {
        return new ALevelPrerequisite(subjectField.getText(), gradeField.getText());
    }

}
