package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import stringen.logic.requirements.MajorPrerequisite;

public class MajorPrerequisiteCard extends HBox {

    @FXML
    private TextField majorCodeField;

    private HBox parent;

    public MajorPrerequisiteCard(HBox parent) {
        try {
            this.parent = parent;
            FXMLLoader fxmlLoader = new FXMLLoader(MajorPrerequisiteCard.class
                    .getResource("/view/MajorPrerequisiteCard.fxml"));
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
        parent.getChildren().add(new MajorPrerequisiteCard(parent));
    }

    public MajorPrerequisite getMajorPrerequisite() {
        return new MajorPrerequisite(majorCodeField.getText());
    }

}
