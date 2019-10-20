package stringen.ui;

import java.io.IOException;
import java.util.Iterator;

import javax.print.attribute.standard.MediaSize;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import stringen.logic.Module;

public class EntryDialog extends AnchorPane {

    @FXML
    private ComboBox<String> cohortStart;

    @FXML
    private ComboBox<String> cohortEnd;

    @FXML
    private ComboBox<String> otherRequirement;

    @FXML
    private ComboBox<String> modulePrefix;

    @FXML
    private Button addCohortButton;

    @FXML
    private Button addOtherSubRequirementButton;

    @FXML
    private Button addSubRequirementButton;

    @FXML
    private VBox mcSubRequirements;

    @FXML
    private VBox otherRequirements;


    public EntryDialog() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CommandBox.class.getResource("/view/EntryDialog.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        cohortStart.getItems().addAll("2011", "2012", "2013");
        cohortStart.getSelectionModel().select("YEAR");
        cohortEnd.getItems().addAll("2011", "2012", "2013");
        cohortEnd.getSelectionModel().select("YEAR");
    }

    public Module updateModule(Module module) {
        String startYear = cohortStart.getSelectionModel().getSelectedItem();
        String endYear = cohortEnd.getSelectionModel().getSelectedItem();
        module.addNewCohort(startYear, endYear);
        //...
        return module;
    }

    @FXML
    private void createNewPane() {
        Parent parent = this.getParent();
        VBox entryDialogs = (VBox) parent;
        entryDialogs.getChildren().add(new EntryDialog());
        this.getChildren().remove(addCohortButton);
    }

    @FXML
    private void addSubRequirement() {
        mcSubRequirements.getChildren().remove(addSubRequirementButton);
        mcSubRequirements.getChildren().add(new McSubRequirement());
        mcSubRequirements.getChildren().add(addSubRequirementButton);
    }

    @FXML
    private void createNewOtherSubRequirement() {
        otherRequirements.getChildren().remove(addOtherSubRequirementButton);
        ObservableList<Node> children = otherRequirements.getChildren();
        for (Node child : children) {
            if (child instanceof OtherSubRequirement) {
                OtherSubRequirement childRequirements = (OtherSubRequirement) child;
                childRequirements.enableOptionBox();
            }
        }
        otherRequirements.getChildren().add(new OtherSubRequirement());
        otherRequirements.getChildren().add(new StackPane());
        otherRequirements.getChildren().add(addOtherSubRequirementButton);

    }

}


