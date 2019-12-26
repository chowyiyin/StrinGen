package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import stringen.logic.requirements.MajorPrerequisite;

public class ModuleConcurrentCard extends HBox {

    @FXML
    private TextField moduleCodeField;

    private HBox parent;

    public ModuleConcurrentCard(HBox parent) {
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
        getChildren().remove(getChildren().size() - 1);
        Label label = new Label("OR");
        label.getStyleClass().add("label");
        getChildren().add(label);
        parent.getChildren().add(new ModuleConcurrentCard(parent));
    }

    public MajorPrerequisite getModuleConcurrent() {
        return new MajorPrerequisite(moduleCodeField.getText());
    }

}
