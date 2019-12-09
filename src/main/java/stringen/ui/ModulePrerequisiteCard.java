package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ModulePrerequisiteCard extends HBox {

    private EntryFieldCard parent;

    public ModulePrerequisiteCard(EntryFieldCard parent) {
        try {
            this.parent = parent;
            FXMLLoader fxmlLoader = new FXMLLoader(ModulePrerequisiteCard.class
                    .getResource("/view/ModulePrerequisiteCard.fxml"));
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
        parent.getChildren().add(new ModulePrerequisiteCard(parent));
    }

}
