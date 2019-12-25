package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import stringen.logic.prerequisites.MajorPreclusion;

public class MajorPreclusionCard extends HBox {

    @FXML
    private TextField majorCodeField;

    private HBox parent;

    public MajorPreclusionCard(HBox parent) {
        try {
            this.parent = parent;
            FXMLLoader fxmlLoader = new FXMLLoader(MajorPreclusionCard.class
                    .getResource("/view/MajorPreclusionCard.fxml"));
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
        parent.getChildren().add(new MajorPreclusionCard(parent));
    }

    public MajorPreclusion getMajorPreclusion() {
        return new MajorPreclusion(majorCodeField.getText());
    }

}
